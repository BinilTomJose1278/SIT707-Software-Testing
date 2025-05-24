FROM python:3.10-slim
WORKDIR /app
COPY . .
RUN pip install -r requirements.txt

# Expose the port
EXPOSE 8080

# Set the PORT environment variable
ENV PORT=8080

CMD ["python", "app.py"]