### Build l'image docker
sudo docker build -t notes-jdk21:lastet .

### Supprime les containers intermédiaire
sudo docker system prune

### Visibilité des images
sudo docker images

### Visibilité des containers
sudo docker ps -a

### Build le docker compose
sudo docker-compose up

### Ferme les containers ouverts
sudo docker-compose down

[launcher]
- Build l'image de docker
- Lancer le docker compose