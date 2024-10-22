from django.db import models

# Dimensión: Actor
class Actor(models.Model):
    name = models.CharField(max_length=255)
    actor_type = models.CharField(max_length=255)

    def __str__(self):
        return self.name

# Dimensión: Tipo de Ataque
class TipoAtaque(models.Model):
    tipo = models.CharField(max_length=255)

    def __str__(self):
        return self.tipo

# Dimensión: Región
class Region(models.Model):
    name = models.CharField(max_length=255)

    def __str__(self):
        return self.name

# Dimensión: Organización (para CISSM)
class Organization(models.Model):
    name = models.CharField(max_length=255)

    def __str__(self):
        return self.name

# Tabla de Hechos: Incidente
class Incidente(models.Model):
    description = models.TextField()  # Descripción del incidente
    event_date = models.CharField(max_length=255)  # Fecha del incidente
    day = models.IntegerField(null=True, blank=True)  # Día del evento (permitir nulos)
    month = models.IntegerField(null=True, blank=True)  # Mes del evento (permitir nulos)
    year = models.IntegerField()  # Año del evento
    motive = models.CharField(max_length=255, null=True, blank=True)  # Motivo del incidente
    motive_code = models.FloatField(null=True, blank=True)

    # Relación con dimensiones
    actor = models.ForeignKey(Actor, on_delete=models.CASCADE)
    tipo_ataque = models.ForeignKey(TipoAtaque, on_delete=models.CASCADE)
    region = models.ForeignKey(Region, on_delete=models.CASCADE)
    organization = models.ForeignKey(Organization, null=True, blank=True, on_delete=models.CASCADE)

    def __str__(self):
        return f"Incidente de {self.actor} en {self.organization} ({self.year})"

