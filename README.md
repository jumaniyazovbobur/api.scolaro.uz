Live links payme documentation

1. [Инициализация платежей document](https://developer.help.paycom.uz/initsializatsiya-platezhey/otpravka-cheka-po-metodu-get)
2. [Method merchant api doc](https://developer.help.paycom.uz/metody-merchant-api/)

Paymedan balance toldirish uchun qilinadigan ishlar:

1. Frontend yoki mobiledan qancha sum ga toldirmoqchi bolgani olinadi masalan 1000 (bu backend ga tiyinda yuboriladi
   1000 * 100 = 100 000) `/api/v1/transaction/student/fill-callback` shu endpoint ga `POST` request yuboriladi.
```
{
    "id": "string",
    "status": "CREATED",
    "profileId": "string",
    "amount": 0,
    "transactionType": "DEBIT",
    "url": "string"
 }
``` 
success bolgan holatda shunday response qaytadi `url` ni olib tolov qilish buttonga qoyiladi yoki osha url ga redirect qilinadi.