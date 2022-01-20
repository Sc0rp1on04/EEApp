"""demo_project URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin

from django.urls import path, include
from django.conf import settings

from django.conf.urls.static import static
 
from demoapp import views 

urlpatterns = [
    path('', views.index, name='person_index_route'),
    path('admin/', admin.site.urls),
    path('demo/', views.index),
    path('accounts/', include('accounts.urls')), 
    path('accounts/', include('django.contrib.auth.urls')),  
    # 1.) people path routes to index 
    path('people/', views.index, name='person_index_route'), 
    # 2.) people path followed by the id routes to the person details with this id 
    path('people/<int:person_id>/', views.details, name='person_details_route'), 
    # people followed by create routes to the person create page 
    path('people/create/', views.create, name='person_create_route'), 
    # people followed by store routes to the person store function 
    path('people/store/', views.store, name='person_store_route'), 
    # people followed by edit and the id routes to edit form 
    path('people/edit/<int:person_id>/', views.edit, name='person_edit_route'), 
    # people followed by update and the id routes to the update function 
    path('people/update/<int:person_id>/', views.update, name='person_update_route'), 
    #people followed by delete and the id routes to the delete function 
    path('people/delete/<int:person_id>/', views.delete, name='person_delete_route'),

    
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)


