# Generated by Django 3.2.9 on 2021-11-25 21:06

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('demoapp', '0002_qrcode'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='person',
            name='age',
        ),
        migrations.RemoveField(
            model_name='person',
            name='description',
        ),
        migrations.RemoveField(
            model_name='person',
            name='sex',
        ),
        migrations.AddField(
            model_name='person',
            name='vsclass',
            field=models.CharField(default=1, max_length=100),
            preserve_default=False,
        ),
    ]
