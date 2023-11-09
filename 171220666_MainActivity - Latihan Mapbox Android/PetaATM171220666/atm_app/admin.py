from django.contrib import admin
from .models import ATM

@admin.register(ATM)
class ATMAdmin(admin.ModelAdmin):
    list_display = ('namaATM', 'alamat', 'latitude', 'longitude')
