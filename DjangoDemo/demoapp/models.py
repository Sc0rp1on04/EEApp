from django.db import models
# Create your models here.
import random
from django.db import models
import qrcode
from PIL import Image, ImageDraw
from io import BytesIO
from django.core.files import File

# Create your models here.

class Person(models.Model): 
    firstname = models.CharField(max_length=100) 
    lastname = models.CharField(max_length=100) 
    vsclass = models.CharField(max_length=100) 

def __str__(self): 
    return self.firstname + ' ' + self.lastname 



class QrCode(models.Model):
   url=models.URLField()
   image=models.ImageField(upload_to='qrcode',blank=True)

   def save(self,*args,**kwargs):
      qrcode_img=qrcode.make(self.url)
      canvas=Image.new("RGB", (300,300),"white")
      draw=ImageDraw.Draw(canvas)
      canvas.paste(qrcode_img)
      buffer=BytesIO()
      canvas.save(buffer,"PNG")
      self.image.save(f'image{random.randint(0,9999)}',File(buffer),save=False)
      canvas.close()
      super().save(*args,**kwargs)