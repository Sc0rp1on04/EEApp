from django.shortcuts import render
from django.http import HttpResponse
import json

from django.shortcuts import render 
from django.http import Http404 

import firebase_admin
from firebase_admin import credentials
from firebase_admin import auth
from firebase_admin import db

cred = credentials.Certificate("./eeapp-d4a14-firebase-adminsdk-z9an8-a11d23246c.json")
firebase_admin.initialize_app(cred, {'databaseURL': 'https://eeapp-d4a14-default-rtdb.europe-west1.firebasedatabase.app/'})

# Create your views here. 
def index(request): 
    return HttpResponse("<h1>Hello Django!</h1>") 

from .models import Person 

# the index view returns all people with the index template 
def index(request): 
    people = Person.objects.all()  # get all Person opjects from the database 
    # return the rendered template page with the passed parameter people 

    persons = {}
    
    for person in people: 
        person.id
        data = {}
        j = {}
        j["firstname"] = person.firstname
        j["lastname"] = person.lastname
        j["vsclass"] = person.vsclass
        token = auth.create_custom_token(person.firstname + person.lastname + person.vsclass)
        j["token"] = token.decode("utf-8")
        
        persons[person.firstname + person.lastname + person.vsclass] = {}
        persons[person.firstname + person.lastname + person.vsclass]["json"] = json.dumps(j)
        persons[person.firstname + person.lastname + person.vsclass]["p"] = person

    #return render(request, 'person/index.html', {'people': people})
    return render(request, 'person/index.html', {'persons': persons})




# the details view returns one person with the detail template 
def details(request, person_id): 
    try: 
        # try to get the Person object with the person_id 
        person = Person.objects.get(id=person_id) 

    except Person.DoesNotExist: 
        # if the Person object was not found raise a 404 Http error 
        raise Http404('Person not found') 

    data = {}
    j = {}
    j["firstname"] = person.firstname
    j["lastname"] = person.lastname
    j["vsclass"] = person.vsclass

    data["person"] = person
    data["json"] = json.dumps(j)
        
    # return the rendered template page with the passed parameter person
    return render(request, 'person/details.html', {'data': data}) 


# the create() function returns an empty form to enter the new person data 
def create(request): 
    return render(request, 'person/create.html') 

from django.http import HttpResponseBadRequest 
from django.shortcuts import redirect 

# the store() function stores the new person and redirects back to the person_index_route 
def store(request): 
    try: 
        p = Person() 
        p.firstname = request.POST.get('firstname') 
        p.lastname = request.POST.get('lastname') 
        p.vsclass = request.POST.get('vsclass') 

        p.save() 
        return redirect('person_index_route') 
    except Exception as ex: 
        return HttpResponseBadRequest(ex) 

def edit(request, person_id): 
    try: 
        # try to get the Person object with the person_id 
        person = Person.objects.get(id=person_id) 
    except Person.DoesNotExist: 
        # if the Person object was not found raise a 404 Http error 
        raise Http404('Person not found') 
    return render(request, 'person/edit.html', {'person': person}) 


def update(request, person_id): 
    try: 
        p = Person.objects.get(id=person_id) 
        p.firstname = request.POST.get('firstname') 
        p.lastname = request.POST.get('lastname') 
        p.vsclass = request.POST.get('vsclass') 
        p.age = request.POST.get('age') 
        p.sex = request.POST.get('sex') 
        p.description = request.POST.get('description') 
        p.save() 
    except Person.DoesNotExist: 
        raise Http404('Person not found') 
    except Exception as ex: 
        return HttpResponseBadRequest(ex) 
    return redirect('person_index_route') 

def delete(request, person_id): 
    try: 
        # try to get the Person object with the person_id 
        person = Person.objects.get(id=person_id) 
        person.delete() 
    except Person.DoesNotExist: 
        # if the Person object was not found raise a 404 Http error 
        raise Http404('Person not found') 
    except Exception as ex: 
        return HttpResponseBadRequest(ex) 
    return redirect('person_index_route') 


from django.contrib.auth.decorators import login_required 

@login_required 
def person_create(request): 
    departments = Department.objects.all() 
    sex_choices = Person.SEX_CHOICES 
    return render(request, 'person/create.html', {'departments': departments, 'sex_choices': sex_choices}) 