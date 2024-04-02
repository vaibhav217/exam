import base64
import hashlib
from flask import Flask, render_template

app = Flask(__name__, static_url_path='/static', static_folder='static')
app.config['DEBUG'] = True

@app.route("/")
def start():
    username = "admin"  
    iv = "padding"
    csrf = username + iv
    csrf_hash = hashlib.sha256(csrf.encode()).hexdigest()

    csrf_token = base64.b64encode(csrf_hash.encode()).decode('utf-8')

    return render_template("evil_csrf.html", csrf_token=csrf_token)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=1337)

