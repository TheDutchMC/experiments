# Windows only

from subprocess import check_output
folder_content_as_byt = check_output("dir /B /O input" , shell=True)
folder_content_as_byt_arr = folder_content_as_byt.splitlines()

for item_byt in folder_content_as_byt_arr:
    item_str = item_byt.decode("utf-8")

    if not item_str.endswith(".dat"):
        continue

    print("Converting " + item_str)
    check_output("nbt2json.exe --java -l -i input/" + item_str + " -o output/" + item_str + ".json")
