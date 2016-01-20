all: mvn_release_prepare
PHONY : all

mvn_release_prepare:
	mvn release:prepare

mvn_release_perform:
	mvn release:perform
