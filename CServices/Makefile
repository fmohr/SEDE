.phony: all clean

all:
	gradle classes
	gradle build
	cd cbuild && ./make_imagebridge.sh
	cd cbuild && ./make_pluginbridge.sh
	cd cbuild && ./make_services.sh

clean:
	cd cbuild && rm -rf *.so *.o
