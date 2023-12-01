import json

from django.http import HttpResponse


class tool:

    def __init__(self, code, message):
        self.code = code
        self.message = message

    def res(self):
        data = {
            'code': self.code,
            'message': self.message
        }
        return data
