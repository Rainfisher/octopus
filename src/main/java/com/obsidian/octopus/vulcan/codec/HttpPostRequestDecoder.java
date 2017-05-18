package com.obsidian.octopus.vulcan.codec;

import com.obsidian.octopus.utils.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoderAdapter;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public class HttpPostRequestDecoder extends MessageDecoderAdapter {

    private static final Logger LOGGER = Logger.getInstance(HttpPostRequestDecoder.class);
    private static final byte[] CONTENT_LENGTH = "Content-Length:".getBytes();
    private final CharsetDecoder decoder = Charset.defaultCharset().newDecoder();

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        if (in.get(0) == (byte) 'P' && in.get(1) == (byte) 'O'
                && in.get(2) == (byte) 'S' && in.get(3) == (byte) 'T') {
            int remaining = in.remaining();
            int last = remaining - 1;
            // Http POST request
            // first the position of the 0x0D 0x0A 0x0D 0x0A bytes
            int eoh = -1;
            for (int i = last; i > 2; i--) {
                if (in.get(i) == (byte) 0x0A && in.get(i - 1) == (byte) 0x0D
                        && in.get(i - 2) == (byte) 0x0A
                        && in.get(i - 3) == (byte) 0x0D) {
                    eoh = i + 1;
                    break;
                }
            }
            if (eoh == -1) {
                return NEED_DATA;
            }
            for (int i = 0; i < last; i++) {
                boolean found = false;
                for (int j = 0; j < CONTENT_LENGTH.length; j++) {
                    if (in.get(i + j) != CONTENT_LENGTH[j]) {
                        found = false;
                        break;
                    }
                    found = true;
                }
                if (found) {
                    // retrieve value from this position till next 0x0D 0x0A
                    StringBuilder contentLength = new StringBuilder();
                    for (int j = i + CONTENT_LENGTH.length; j < last; j++) {
                        if (in.get(j) == 0x0D) {
                            break;
                        }
                        contentLength.append(new String(
                                new byte[]{in.get(j)}));
                    }
                    // if content-length worth of data has been received then the message is complete
                    return Integer.parseInt(contentLength.toString().trim())
                            + eoh == remaining ? OK : NEED_DATA;

                }
            }
            return NEED_DATA;
        }
        return NOT_OK;
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        HttpRequestMessage request = new HttpRequestMessage();
        parseRequest(request, new StringReader(in.getString(decoder)));
        request.setReceivedAt(System.currentTimeMillis());
        out.write(request);
        return OK;
    }

    private void parseRequest(HttpRequestMessage requestMessage, Reader is) {
        BufferedReader rdr = new BufferedReader(is);

        try {
            // Get request URL.
            String line = rdr.readLine();
            String[] url = line.split(" ");
            if (url.length < 3) {
                return;
            }
            Map<String, String> headers = new HashMap<>();
            Map<String, String[]> parameters = new HashMap<>();

            requestMessage.setUri(line);
            requestMessage.setMethod(url[0].toUpperCase());
            requestMessage.setContext(url[1].substring(1));
            requestMessage.setProtocol(url[2]);
            // Read header
            while ((line = rdr.readLine()) != null && line.length() > 0) {
                String[] tokens = line.split(": ");
                headers.put(tokens[0].toLowerCase(), tokens[1]);
            }

            // If method 'POST' then read Content-Length worth of data
            if (url[0].equalsIgnoreCase("POST")) {
                int len = Integer.parseInt(headers.get("content-length"));
                char[] buf = new char[len];
                if (rdr.read(buf) == len) {
                    line = String.copyValueOf(buf);
                }
            }
            if (line != null) {
                line = URLDecoder.decode(line, Charset.defaultCharset().name());
                String[] match = line.split("\\&");
                for (String element : match) {
                    String[] params = new String[1];
                    String[] tokens = element.split("=");
                    switch (tokens.length) {
                        case 0:
                            parameters.put(element.toLowerCase(), new String[]{});
                            break;
                        case 1:
                            parameters.put(tokens[0].toLowerCase(), new String[]{""});
                            break;
                        default:
                            String name = tokens[0].toLowerCase();
                            if (parameters.containsKey(name)) {
                                params = parameters.get(name);
                                String[] tmp = new String[params.length + 1];
                                System.arraycopy(params, 0, tmp, 0, params.length);
                                params = tmp;
                            }
                            params[params.length - 1] = tokens[1].trim();
                            parameters.put(name, params);
                    }
                }
            }
            requestMessage.setHeaders(headers);
            requestMessage.setParameters(parameters);
        }
        catch (IOException ex) {
            LOGGER.debug("parseRequest", ex);
        }
    }

}
