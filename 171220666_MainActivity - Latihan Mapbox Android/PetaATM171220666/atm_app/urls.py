from django.urls import path
from . import views

urlpatterns = [
    path('tambah/', views.tambah_atm, name='tambah_atm'),
    path('lihat/', views.lihat_atm, name='lihat_atm'),
    path('peta/', views.tampilkan_peta, name='tampilkan_peta'),
]
