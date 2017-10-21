# Décisions Architeturale
_ce fichier contient la description de nos décisions d'architecture logiciel_
##Board
- Changer ArrayList(Cell) en HashMap(Point, Cell)

- Stocker Irrigation en HashMap()
  - Trouver si un axe est irriguable entre Cell1 et Cell2 :</br>
  Faire la différence entre getNeighboors(Cell1) et getNeigboors(Cell2).
- Creation d'une fonction getNeigthbours qui renvoies les cases adjacentes a un element.
 _(voire OKA-32)_
 