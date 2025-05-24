from flask import Flask, request, jsonify, send_from_directory

app = Flask(__name__)
expenses = []
next_id = 1

@app.route("/")
def serve_frontend():
    return send_from_directory("static", "index.html")

@app.route("/expenses", methods=["POST"])
def add_expense():
    global next_id
    data = request.get_json()
    data["id"] = next_id
    next_id += 1
    expenses.append(data)
    return jsonify({"message": "Expense added", "id": data["id"]}), 201

@app.route("/expenses", methods=["GET"])
def get_expenses():
    return jsonify(expenses)

@app.route("/expenses/<int:id>", methods=["GET"])
def get_expense_by_id(id):
    for e in expenses:
        if e["id"] == id:
            return jsonify(e)
    return jsonify({"error": "Not found"}), 404

@app.route("/expenses/<int:id>", methods=["PUT"])
def update_expense(id):
    data = request.get_json()
    for e in expenses:
        if e["id"] == id:
            e.update(data)
            return jsonify({"message": "Updated"})
    return jsonify({"error": "Not found"}), 404

@app.route("/expenses/<int:id>", methods=["DELETE"])
def delete_expense(id):
    global expenses
    expenses = [e for e in expenses if e["id"] != id]
    return jsonify({"message": "Deleted"})

@app.route("/expenses/search", methods=["GET"])
def search_expenses():
    item = request.args.get("item", "").lower()
    result = [e for e in expenses if item in e.get("item", "").lower()]
    return jsonify(result)

@app.route("/expenses/total", methods=["GET"])
def get_total():
    total = sum(e.get("amount", 0) for e in expenses)
    return jsonify({"total": total})

@app.route("/expenses", methods=["DELETE"])
def clear_expenses():
    global expenses
    expenses = []
    return jsonify({"message": "All expenses cleared"})

@app.route("/expenses/stats", methods=["GET"])
def get_stats():
    count = len(expenses)
    total = sum(e.get("amount", 0) for e in expenses)
    avg = total / count if count > 0 else 0
    return jsonify({"count": count, "average": avg})

@app.route("/expenses/sorted", methods=["GET"])
def sort_expenses():
    by = request.args.get("by", "amount")
    sorted_list = sorted(expenses, key=lambda e: e.get(by, ""))
    return jsonify(sorted_list)

if __name__ == "__main__":
    app.run(debug=True)
