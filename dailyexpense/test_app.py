from app import app

client = app.test_client()

def reset():
    client.delete("/expenses")

def test_home(): res = client.get("/"); assert res.status_code == 200
def test_add_expense(): reset(); res = client.post("/expenses", json={"item": "Coffee", "amount": 5}); assert res.status_code == 201
def test_add_missing_amount(): res = client.post("/expenses", json={"item": "Tea"}); assert res.status_code == 201
def test_get_expenses(): reset(); client.post("/expenses", json={"item": "Snacks", "amount": 3}); res = client.get("/expenses"); assert isinstance(res.get_json(), list)
def test_total_value(): reset(); client.post("/expenses", json={"item": "A", "amount": 4}); client.post("/expenses", json={"item": "B", "amount": 6}); res = client.get("/expenses/total"); assert res.get_json()["total"] == 10
def test_delete_expense(): reset(); res = client.post("/expenses", json={"item": "Temp", "amount": 2}); expense_id = res.get_json()["id"]; del_res = client.delete(f"/expenses/{expense_id}"); assert del_res.status_code == 200
def test_search_expenses(): reset(); client.post("/expenses", json={"item": "Lunch", "amount": 12}); res = client.get("/expenses/search?item=lunch"); assert len(res.get_json()) >= 1
def test_clear_expenses(): client.delete("/expenses"); res = client.get("/expenses"); assert res.get_json() == []
def test_get_by_id(): reset(); res = client.post("/expenses", json={"item": "ID Test", "amount": 8}); expense_id = res.get_json()["id"]; get_res = client.get(f"/expenses/{expense_id}"); assert get_res.status_code == 200 and get_res.get_json()["item"] == "ID Test"
def test_get_invalid_id(): res = client.get("/expenses/9999"); assert res.status_code == 404
def test_update_expense(): reset(); res = client.post("/expenses", json={"item": "Pen", "amount": 1}); expense_id = res.get_json()["id"]; update = client.put(f"/expenses/{expense_id}", json={"amount": 3}); assert update.status_code == 200
def test_update_invalid_id(): res = client.put("/expenses/9999", json={"amount": 20}); assert res.status_code == 404
def test_delete_invalid(): res = client.delete("/expenses/9999"); assert res.status_code == 200
def test_get_stats(): reset(); client.post("/expenses", json={"item": "Apple", "amount": 5}); client.post("/expenses", json={"item": "Banana", "amount": 15}); res = client.get("/expenses/stats"); stats = res.get_json(); assert stats["count"] == 2 and round(stats["average"]) == 10
def test_negative_amount(): res = client.post("/expenses", json={"item": "Refund", "amount": -10}); assert res.status_code == 201
def test_add_no_json(): res = client.post("/expenses"); assert res.status_code in [400, 415]
def test_add_empty_item(): res = client.post("/expenses", json={"item": "", "amount": 5}); assert res.status_code == 201
def test_sort_by_amount(): reset(); client.post("/expenses", json={"item": "Zebra", "amount": 20}); client.post("/expenses", json={"item": "Alpha", "amount": 5}); res = client.get("/expenses/sorted?by=amount"); data = res.get_json(); assert data[0]["amount"] <= data[1]["amount"]
def test_sort_by_item(): res = client.get("/expenses/sorted?by=item"); data = res.get_json(); assert data[0]["item"] <= data[1]["item"]
def test_verify_deletion(): reset(); res = client.post("/expenses", json={"item": "Temp", "amount": 7}); expense_id = res.get_json()["id"]; client.delete(f"/expenses/{expense_id}"); get = client.get(f"/expenses/{expense_id}"); assert get.status_code == 404
