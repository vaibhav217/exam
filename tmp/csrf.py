import base64
import hashlib
from flask import Flask, render_template

app = Flask(__name__, static_url_path='/static', static_folder='static')
app.config['DEBUG'] = True

@app.route("/")
def start():
    # Generate a CSRF token based on a username and IV
    username = "admin"
    iv = "padding"
    csrf = username + iv
    csrf_hash = hashlib.sha256(csrf.encode()).hexdigest()
    csrf_token = base64.b64encode(csrf_hash.encode()).decode('utf-8')
    
    # Render the HTML template with the CSRF token
    return render_template("evil_csrf2.html", csrf_token=csrf_token)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=1337)

