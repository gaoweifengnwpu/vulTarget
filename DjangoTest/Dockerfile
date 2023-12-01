# pull the official base image

FROM  python:3.9.18-alpine
RUN mkdir -p /usr/src/app
# set work directory
WORKDIR /usr/src/app
# set environment variables
ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1
# copy project
COPY . /usr/src/app
RUN pip install --upgrade pip
RUN pip install -r requirements.txt
#EXPOSE 8000
#ENV PYTHONPATH="$PYTHONPATH:/usr/src/app"
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]