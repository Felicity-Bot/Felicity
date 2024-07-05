from http.server import BaseHTTPRequestHandler, HTTPServer
import subprocess
import json
import os

class WebhookHandler(BaseHTTPRequestHandler):
    def do_POST(self):
        if self.path == '/deploy':
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            payload = json.loads(post_data.decode('utf-8'))

            # You can add validation of the payload here if needed

            # Execute the deploy script
            process = subprocess.Popen(['/home/misfit/projects/Felicity/deploy.sh'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

            # Capture the output and print it to the terminal
            for line in process.stdout:
                print(line, end='')

            for line in process.stderr:
                print(line, end='', file=sys.stderr)

            # Send response
            self.send_response(200)
            self.end_headers()
            self.wfile.write(b'OK')

def run(server_class=HTTPServer, handler_class=WebhookHandler, port=80):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print(f'Starting server on port {port}...')
    httpd.serve_forever()

if __name__ == '__main__':
    run()
