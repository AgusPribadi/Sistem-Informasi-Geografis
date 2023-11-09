from django.db import models

class ATM(models.Model):
    namaATM = models.CharField(max_length=100)
    alamat = models.CharField(max_length=200)
    latitude = models.FloatField()
    longitude = models.FloatField()