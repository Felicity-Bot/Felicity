from http.server import BaseHTTPRequestHandler, HTTPServer
import subprocess
import json

class WebhookHandler(BaseHTTPRequestHandler):
    def do_POST(self):
        if self.path == '/deploy':
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            payload = json.loads(post_data.decode('utf-8'))

            # You can add validation of the payload here if needed

            # Execute the deploy script
            subprocess.call(['/path/to/your/project/deploy.sh'])

            self.send_response(200)
            self.end_headers()
            self.wfile.write(b'OK')

def run(server_class=HTTPServer, handler_class=WebhookHandler, port=8080):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print(f'Starting server on port {port}...')
    httpd.serve_forever()

if __name__ == '__main__':
    run()
