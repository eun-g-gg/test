  class CartItem {
    final String id;
    final String title;
    final String thumbnail;
    final int price;
    int quantity;
    bool isSelected;

    CartItem({
      required this.id,
      required this.title,
      required this.price,
      required this.thumbnail,
      this.quantity = 1,
      this.isSelected = false,
    });

    // JSON에서 CartItem 객체를 생성하는 factory 생성자
    factory CartItem.fromJson(Map<String, dynamic> json) {
      return CartItem(
        id: json['product']['id'].toString(),
        title: json['product']['name'],
        thumbnail : json['product']['thumbnail'],
        price: json['product']['price'] as int,
        quantity: json['quantity'] as int,
      );
    }

    // CartItem 객체를 JSON으로 변환하는 메서드
    Map<String, dynamic> toJson() {
      return {
        'productId': id,
        'quantity': quantity,
      };
    }

    void toggleSelection() {
      isSelected = !isSelected;
    }
  }