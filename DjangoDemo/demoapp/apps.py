from django.apps import AppConfig


class DemoappConfig(AppConfig):
    name = 'demoapp'


INSTALLED_APPS = (
    # ...,
    'qr_code',
)