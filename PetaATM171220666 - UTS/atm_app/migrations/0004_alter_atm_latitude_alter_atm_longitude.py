# Generated by Django 4.2.7 on 2023-11-02 11:02

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("atm_app", "0003_alter_atm_latitude_alter_atm_longitude"),
    ]

    operations = [
        migrations.AlterField(
            model_name="atm", name="latitude", field=models.FloatField(),
        ),
        migrations.AlterField(
            model_name="atm", name="longitude", field=models.FloatField(),
        ),
    ]
