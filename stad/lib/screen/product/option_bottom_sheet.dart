import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stad/constant/colors.dart';
import 'package:stad/models/cart_model.dart';
import 'package:stad/models/product_model.dart';
import 'package:stad/providers/user_provider.dart';
import 'package:stad/screen/order/order_screen.dart';
import 'package:stad/services/cart_service.dart';
import 'package:stad/widget/custom_dropdown.dart';
import 'package:stad/widget/page_animation.dart';
import 'package:stad/widget/quantity_changer.dart';

// 모달 바텀 시트를 띄우는 함수
void showProductOptionBottomSheet(
    BuildContext context,
    ProductInfo? productInfo,
    List<ProductType> productTypes,
    String title,
    int advertId,
    int contentId) {
  showModalBottomSheet(
    isScrollControlled: true,
    context: context,
    builder: (BuildContext context) {
      return ProductOptionBottomSheet(
        productInfo: productInfo,
        productTypes: productTypes,
        title: title,
        advertId: advertId,
        contentId: contentId,
      );
    },
  );
}

// 바텀 시트의 내용을 관리할 StatefulWidget
class ProductOptionBottomSheet extends StatefulWidget {
  final ProductInfo? productInfo;
  final List<ProductType> productTypes;
  final String title;
  final int advertId;
  final int contentId;

  const ProductOptionBottomSheet({
    super.key,
    this.productInfo,
    required this.productTypes,
    required this.title,
    required this.advertId,
    required this.contentId,
  });

  @override
  _ProductOptionBottomSheetState createState() =>
      _ProductOptionBottomSheetState();
}

class _ProductOptionBottomSheetState extends State<ProductOptionBottomSheet> {
  String? selectedProductOption;
  String? selectedOption;
  bool isProductExpanded = false;
  bool isOptionExpanded = false;
  int? selectedProductIndex;
  int? selectedOptionIndex;
  List<ProductType> selectedProducts = [];
  Map<int, int> quantities = {};
  List<int> optionIds = [];
  final CartService _cartService = CartService();

  void addToCart() async {
    List<CartProductDetail> products = selectedProducts.map((product) {
      return CartProductDetail(
        productTypeId: product.id,
        quantity: quantities[product.id] ?? 0,
        advertId: widget.advertId,
        contentId: widget.contentId,
        optionId: optionIds.isNotEmpty ? optionIds[0] : -1, // 예시로 첫 번째 옵션을 사용
      );
    }).toList();

    int userId = Provider.of<UserProvider>(context, listen: false).userId ?? 0;
    await _cartService.addProductToCart(userId, products);
  }

  void selectProductOption(String? value) {
    if (value != null) {
      int index = widget.productTypes.indexWhere((p) => p.name == value);
      addProduct(widget.productTypes.firstWhere((p) => p.name == value));
      setState(
        () {
          selectedProductIndex = index;
          selectedOptionIndex = null;
          isProductExpanded = false;
          if (widget.productTypes[index].productOptions.isEmpty) {
            isOptionExpanded = false;
          }
          if (!selectedProducts.contains(widget.productTypes[index])) {
            selectedProducts.add(widget.productTypes[index]);
          }
        },
      );
    }
  }

  void _navigateToOrderScreen() {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => OrderScreen(
          productInfo: widget.productInfo,
          // 선택된 제품 정보
          productTypes: selectedProducts,
          // 선택된 제품 유형 리스트
          quantities: quantities,
          deliveryFee: 2500,
          //선택한 수량
          title: widget.title,
          optionIds: optionIds,
          advertId: widget.advertId,
          contentId: widget.contentId,
        ),
      ),
    );
  }

  void addProduct(ProductType product) {
    if (!quantities.containsKey(product.id)) {
      quantities[product.id] = 1; // 초기 수량 설정
    }
    setState(() {
      quantities.update(product.id, (value) => value + 1);
    });
  }

  // void toggleProductExpanded() {
  //   setState(() {
  //     isProductExpanded = !isProductExpanded;
  //     if (isOptionExpanded) isOptionExpanded = false;
  //   });
  // }

  void toggleOptionExpanded() {
    setState(() {
      isOptionExpanded = !isOptionExpanded;
      if (isProductExpanded) isProductExpanded = false;
    });
  }

  void selectOption(String? option) {
    if (option != null) {
      int optionIndex = widget
          .productTypes[selectedProductIndex!].productOptions
          .indexWhere((o) => o.name == option);
      int optionId = widget.productTypes[selectedProductIndex!]
          .productOptions[optionIndex].value; // 옵션 ID를 가져옴
      setState(() {
        selectedOption = option;
        selectedOptionIndex = optionIndex;
        optionIds.add(optionId); // 옵션 ID를 리스트에 추가
        isOptionExpanded = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    List<String> productOptions =
        widget.productTypes.map((p) => p.name).toList();
    List<String>? currentOptions = selectedProductIndex != null
        ? widget.productTypes[selectedProductIndex!].productOptions
            .map((o) => o.name)
            .toList()
        : null;

    return Container(
      decoration: BoxDecoration(
        color: mainWhite,
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(25.0),
          topRight: Radius.circular(25.0),
        ),
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 16.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Container(
              width: 40,
              height: 4,
              margin: const EdgeInsets.only(bottom: 8.0),
              decoration: BoxDecoration(
                color: darkGray,
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            SizedBox(
              height: 40.0,
            ),
            CustomDropdown(
              title: '상품선택',
              options: productOptions,
              //서버에서 받아올 것
              isExpanded: isProductExpanded,
              selectedOption: selectedProductIndex != null
                  ? productOptions[selectedProductIndex!]
                  : null,
              onToggle: () =>
                  setState(() => isProductExpanded = !isProductExpanded),
              onSelect: selectProductOption,
            ),
            SizedBox(height: 15),
            if (currentOptions != null && currentOptions.isNotEmpty)
              CustomDropdown(
                title: '옵션선택',
                options: currentOptions,
                //서버에서 받아올 것
                isExpanded: isOptionExpanded,
                selectedOption: selectedOptionIndex != null
                    ? currentOptions[selectedOptionIndex!]
                    : null,
                onToggle: toggleOptionExpanded,
                onSelect: (String? value) {
                  setState(() {
                    selectedOptionIndex = currentOptions.indexOf(value!);
                  });
                },
              ),
            if (selectedProductIndex != null) ...[
              ...selectedProducts.map((product) {
                return Column(
                  children: [
                    ProductDetails(
                      productType: product,
                      onCancel: () {
                        setState(() {
                          selectedProducts.remove(product);
                        });
                      },
                    ),
                    Padding(
                      padding: const EdgeInsets.only(bottom: 8.0),
                      child: QuantityChanger(
                        initialQuantity: 1,
                        maxQuantity: product.quantity,
                        onQuantityChanged: (newQuantity) {
                          // Handle quantity change
                        },
                      ),
                    ),
                  ],
                );
              }).toList(),
            ],
            _buildActionButtons(context),
          ],
        ),
      ),
    );
  }

  Widget _buildActionButtons(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(top: 20.0),
      child: Row(
        children: [
          Expanded(
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                foregroundColor: mainNavy,
                textStyle: TextStyle(
                  fontSize: 16,
                ),
                side: BorderSide(color: mainNavy, width: 1),
                surfaceTintColor: mainWhite,
                backgroundColor: mainWhite,
                padding: EdgeInsets.symmetric(vertical: 14),
                // Button background color
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.all(
                    Radius.circular(10.0),
                  ),
                ),
              ),
              onPressed: addToCart,
              child: Text(
                '장바구니 담기',
              ),
            ),
          ),
          SizedBox(width: 10),
          Expanded(
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                foregroundColor: mainWhite,
                textStyle: TextStyle(
                  fontSize: 16,
                ),
                surfaceTintColor: mainNavy,
                backgroundColor: mainNavy,
                padding: EdgeInsets.symmetric(vertical: 14),
                // Button background color
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.all(
                    Radius.circular(10.0),
                  ),
                ),
              ),
              onPressed: _navigateToOrderScreen,
              child: Text(
                '구매하기',
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class ProductDetails extends StatelessWidget {
  final ProductType productType;
  final VoidCallback onCancel;

  const ProductDetails(
      {Key? key, required this.productType, required this.onCancel})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Expanded(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 4.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(productType.name,
                    style:
                        TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                Text("${productType.price}원", style: TextStyle(color: midGray)),
                Text("재고: ${productType.quantity}",
                    style: TextStyle(color: midGray)),
              ],
            ),
          ),
        ),
        IconButton(
          iconSize: 20.0,
          onPressed: onCancel,
          icon: Icon(
            Icons.cancel_rounded,
            color: mainGray,
          ),
        ),
      ],
    );
  }
}
