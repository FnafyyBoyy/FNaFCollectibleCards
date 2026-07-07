from PIL import Image

# Auswahl
typ = input("Typ wählen (1-5): ")

# Erstes Bild je nach Auswahl
bilder = {
    "1": "pngs/basicforbg.png",
    "2": "pngs/basicuncommonforbg.png",
    "3": "pngs/basicrareforbg.png",
    "4": "pngs/basicepicforbg.png",
    "5": "pngs/basiclegendaryforbg.png",
}

if typ not in bilder:
    print("Ungültige Auswahl!")
    exit()

# Bilder laden
img1 = Image.open(bilder[typ]).convert("RGBA")
img2 = Image.open("pic.png").convert("RGBA")
img3 = Image.open("pngs/basiccardexample.png").convert("RGBA")

# Größe bestimmen
width = max(img1.width, img2.width, img3.width)
height = max(img1.height, img2.height, img3.height)

# Transparentes Ergebnisbild
result = Image.new("RGBA", (width, height), (0, 0, 0, 0))

# Übereinander legen
result.alpha_composite(img1, (0, 0))
result.alpha_composite(img2, (0, 0))
result.alpha_composite(img3, (0, 0))

# Speichern
result.save("ergebnis.png")

print("Fertig!")