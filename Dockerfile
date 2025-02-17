# Use a lightweight web server image
FROM nginx:alpine

# Copy the HTML file to the Nginx server
COPY app/index.html /usr/share/nginx/html/index.html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]