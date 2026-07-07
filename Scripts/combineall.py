from PIL import Image
import os
import json

# Typ auswählen
typ = input("Typ wählen (1-5): ")

typen = {
    "1": "pngs/basicforbg.png",
    "2": "pngs/basicuncommonforbg.png",
    "3": "pngs/basicrareforbg.png",
    "4": "pngs/basiclegendaryforbg.png",
    "5": "pngs/basicepicforbg.png",
}

if typ not in typen:
    print("Ungültige Auswahl!")
    exit()

# Erstes Bild (je nach Typ)
overlay1 = Image.open(typen[typ]).convert("RGBA")

# Drittes Bild (immer gleich)
overlay3 = Image.open("pngs/basiccardexample.png").convert("RGBA")

# Ordner
input_ordner = "pics"
output_ordner = "output"
json_ordner = "output_json"

os.makedirs(output_ordner, exist_ok=True)
os.makedirs(json_ordner, exist_ok=True)

# Prefix je nach Typ
prefixe = {
    "1": "common",
    "2": "uncommon",
    "3": "rare",
    "4": "epic",
    "5": "legendary",
}

prefix = prefixe[typ]

for datei in os.listdir(input_ordner):
    if not datei.lower().endswith(".png"):
        continue

    bild = Image.open(os.path.join(input_ordner, datei)).convert("RGBA")

    width = max(overlay1.width, bild.width, overlay3.width)
    height = max(overlay1.height, bild.height, overlay3.height)

    result = Image.new("RGBA", (width, height), (0, 0, 0, 0))

    result.alpha_composite(overlay1, (0, 0))
    result.alpha_composite(bild, (0, 0))
    result.alpha_composite(overlay3, (0, 0))

    name_ohne_ext = os.path.splitext(datei)[0]
    base_name = f"{prefix}_{name_ohne_ext}_card"

    # PNG speichern
    png_path = os.path.join(output_ordner, base_name + ".png")
    result.save(png_path)

    # JSON Inhalt
    json_data = {
        "parent": "fnaf_collectibles:item/basiccard",
        "textures": {
            "layer0": f"fnaf_collectibles:item/{base_name}"
        }
    }

    # JSON speichern
    json_path = os.path.join(json_ordner, base_name + ".json")
    with open(json_path, "w", encoding="utf-8") as f:
        json.dump(json_data, f, indent=2)

print("Fertig! Alle Bilder wurden nach 'output' gespeichert.")

end = input("Irgendwas")