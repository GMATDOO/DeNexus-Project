from django.db import models

# Modelo para HACKMAGEDDON
class HackmageddonIncident(models.Model):
    author = models.CharField(max_length=255)
    target = models.CharField(max_length=255)
    description = models.TextField()
    attack = models.FloatField()
    target_class = models.FloatField()
    attack_class = models.FloatField()
    link = models.URLField()
    tags = models.TextField()
    day = models.IntegerField()
    month = models.IntegerField()
    year = models.IntegerField()
    africa = models.IntegerField()
    asia = models.IntegerField()
    europe = models.IntegerField()
    north_america = models.IntegerField()
    oceania = models.IntegerField()
    south_america = models.IntegerField()

    def __str__(self):
        return f"Incident by {self.author} targeting {self.target}"

# Modelo para CISSM
class CissmIncident(models.Model):
    id = models.CharField(max_length=255, primary_key=True)
    event_description = models.TextField()
    event_date = models.CharField(max_length=255)
    actor = models.CharField(max_length=255)
    actor_type = models.FloatField()
    event_type = models.CharField(max_length=255)
    organization = models.CharField(max_length=255)
    event_subtype = models.CharField(max_length=255)
    motive = models.CharField(max_length=255)
    motive_code = models.FloatField()
    event_source = models.CharField(max_length=255)
    day = models.IntegerField()
    month = models.IntegerField()
    year = models.IntegerField()
    africa = models.IntegerField()
    asia = models.IntegerField()
    australia = models.IntegerField()
    europe = models.IntegerField()
    north_america = models.IntegerField()
    south_america = models.IntegerField()

    def __str__(self):
        return f"Incident by {self.actor} on {self.organization}"

