Changelog for sample-shop-oracle
================================

## 20.8.2018

Styling of pages using bootstrap.
Composition of pages done using thymeleaf layout templates.


## 19.8.2018
Support for basket of selected items.
Features:
- show basket and its items
- add item to basket (via stored procedure `SHOP_Kosik_add_item`)

## 13.8.2018
Initial commit.
Features:
- running application
- connection to oracle 11g database
- product management (CRUD at /admin/products)
- product listing (/shop) with prepared button to add to cart
