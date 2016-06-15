package com.obsidian.octopus.vulcan.codec;

import com.obsidian.octopus.utils.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoderAdapter;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public class HttpGetRequestDecoder extends MessageDecoderAdapter {

    private static final Logger LOGGER = Logger.getInstance(HttpGetRequestDecoder.class);
    private final CharsetDecoder decoder = Charset.defaultCharset().newDecoder();

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        if (in.get(0) == (byte) 'G' && in.get(1) == (byte) 'E'
                && in.get(2) == (byte) 'T') {
            int remaining = in.remaining();
            int last = remaining - 1;
            // Http GET request therefore the last 4 bytes should be 0x0D 0x0A 0x0D 0x0A
            boolean ret = in.get(last) == (byte) 0x0A
                    && in.get(last - 1) == (byte) 0x0D
                    && in.get(last - 2) == (byte) 0x0A && in.get(last - 3) == (byte) 0x0D;
            return ret ? OK : NEED_DATA;
        }
        return NOT_OK;
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        HttpRequestMessage request = new HttpRequestMessage();
        request.setHeaders(parseRequest(new StringReader(in.getString(decoder))));
        request.setReceivedAt(System.currentTimeMillis());
        out.write(request);
        return OK;
    }

    private Map<String, String[]> parseRequest(Reader is) {
        Map<String, String[]> map = new HashMap<>();
        BufferedReader rdr = new BufferedReader(is);

        try {
            // Get request URL.
            String line = rdr.readLine();
            String[] url = line.split(" ");
            if (url.length < 3) {
                return map;
            }

            map.put("URI", new String[]{line});
            map.put("Method", new String[]{url[0].toUpperCase()});
            map.put("Context", new String[]{url[1].substring(1)});
            map.put("Protocol", new String[]{url[2]});
            // Read header
            while ((line = rdr.readLine()) != null && line.length() > 0) {
                String[] tokens = line.split(": ");
                map.put(tokens[0].toLowerCase(), new String[]{tokens[1]});
            }

            // If method 'POST' then read Content-Length worth of data
            if (url[0].equalsIgnoreCase("GET")) {
                int idx = url[1].indexOf('?');
                if (idx != -1) {
                    map.put("Context",
                            new String[]{url[1].substring(1, idx)});
                    line = url[1].substring(idx + 1);
                } else {
                    line = null;
                }
            }
            if (line != null) {
                String[] match = line.split("\\&");
                for (String element : match) {
                    String[] params = new String[1];
                    String[] tokens = element.split("=");
                    switch (tokens.length) {
                        case 0:
                            map.put("@".concat(element).toLowerCase(), new String[]{});
                            break;
                        case 1:
                            map.put("@".concat(tokens[0]).toLowerCase(), new String[]{""});
                            break;
                        default:
                            String name = "@".concat(tokens[0]).toLowerCase();
                            if (map.containsKey(name)) {
                                params = map.get(name);
                                String[] tmp = new String[params.length + 1];
                                System.arraycopy(params, 0, tmp, 0, params.length);
                                params = tmp;
                            }
                            params[params.length - 1] = tokens[1].trim();
                            map.put(name, params);
                    }
                }
            }
        } catch (IOException ex) {
            LOGGER.debug("parseRequest", ex);
        }

        return map;
    }

}
