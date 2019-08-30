import os
from json import load as readjson
from json import dump as writejson

def main():
    rootdir = "testrsc"
    for root, subFolders, files in os.walk(rootdir):
        for filename in files:
            filename = str(filename)
            if not filename.endswith(".json"):
                continue
            filename = str(root + "/" + filename)
            print("{}".format(filename))
            try:
                with open(filename, "r") as fp:
                    content = readjson(fp)
                with open(filename, "w") as fp:
                    writejson(content, fp, indent=4)
            except Exception as e:
                print("Error {} working on {}.", e, filename)


if __name__ == "__main__": main()
