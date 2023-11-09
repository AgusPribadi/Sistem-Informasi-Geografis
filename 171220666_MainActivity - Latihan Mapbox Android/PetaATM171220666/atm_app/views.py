from django.shortcuts import render, redirect
from .models import ATM
from .forms import ATMForm


def tambah_atm(request):
    if request.method == 'POST':
        form = ATMForm(request.POST)
        if form.is_valid():
            form.save()
            return redirect('lihat_atm')  # Redirect ke halaman "lihat"
    else:
        form = ATMForm()

    return render(request, 'atm_app/tambah.html', {'form': form})

def lihat_atm(request):
    data_atm = ATM.objects.all()
    return render(request, 'atm_app/lihat.html', {'data_atm': data_atm})

def tampilkan_peta(request):
    data_atm = ATM.objects.all()
    return render(request, 'atm_app/peta.html', {'data_atm': data_atm})
