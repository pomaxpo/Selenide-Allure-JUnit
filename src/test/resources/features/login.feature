#language:ru
#encoding:utf-8

@test
Функционал: Авторизация и Регисрация

  @Login
  Сценарий: Авторизация. Ввод коректных данных
    Дано пользователь открывает страницу Webtrader
    * пользователь находится на странице "Логин"
    * он заполняет поле "Email" значением "tes12321312331t@ya.ru"
    * он заполняет поле "Пароль" значением "4Ba4E72D"
    * он нажимает кнопку "Login"
    * пользователь находится на странице "Торговый терминал"
    * отображается элемент "График"

  @LoginInvalid
  Сценарий: Авторизация. Ввод невалидных данных. Активномть кнопки логин
    Дано пользователь открывает страницу Webtrader
    * пользователь находится на странице "Логин"
    * он нажимает кнопку "Login"
    * атрибут "class" элемента "Login" содержит значение "enabled_false"
    * на странице отображается текст "Email: The value can't be empty"
    * на странице отображается текст "Password: The value can't be empty"
    * он заполняет поле "Email" значением "test"
    * на странице отображается текст "Email: Wrong E-mail format"
    * он заполняет поле "Пароль" значением "123321123"
    * на странице отображается текст "Password: The password must be between 6 and 15 characters (letters of the English alphabet) and contain at least one digit."
    * пользователь заполняет поле "Email" случайным значением и запоминает как "Email"
    * он заполняет поле "Пароль" значением "4Ba4E72D"
    * он нажимает кнопку "Login"
    * на странице отображается текст "Wrong login/password"

  @Registration
  Сценарий: Регистрация. Ввод корректных данных
    Дано пользователь открывает страницу Webtrader
    * пользователь находится на странице "Логин"
    * он нажимает кнопку "Регистрация"
    Дано пользователь находится на странице "Регистрация"
    * пользователь заполняет поле "Email" случайным значением и запоминает как "Email"
    * пользователь заполняет поле "Имя" случайным буквенным значением
    * пользователь заполняет поле "Фамилия" случайным буквенным значением
    * он выбирает "Albania: AL +355" из выпадающего списка "Код страны"
    * пользователь заполняет поле "Телефон" случайным цыфровым значением
    * он нажимает кнопку "Открыть счет"
    Дано пользователь находится на странице "Торговый терминал"
    * отображается элемент "Детали аккаунта"
    * пользователь нажимает кнопку "Закрыть"

  @RegistrationInvalid
    #BAG в тексте ошибки вместо First/Given/Middle - fname, аналогично с фамилей lname
  Сценарий: Регистрация. Ввод невалидных данных.
    Дано пользователь открывает страницу Webtrader
    * пользователь находится на странице "Логин"
    * он нажимает кнопку "Регистрация"
    Дано пользователь находится на странице "Регистрация"
    * он нажимает кнопку "Открыть счет"
    * атрибут "class" элемента "Открыть счет" содержит значение "enabled_false"
    * на странице отображается текст "Email: The value can't be empty"
    * на странице отображается текст "First/Given/Middle name(s): The value can't be empty"
    * на странице отображается текст "Phone number: The value can't be empty"
    * на странице отображается текст "Last/Family/Surname(s): The value can't be empty"
    * он заполняет поле "Email" значением "test"
    * на странице отображается текст "Email: Wrong E-mail format"
    * пользователь заполняет поле "Email" случайным значением и запоминает как "Email"
    * он заполняет поле "Имя" значением "3213123"
    * он заполняет поле "Фамилия" значением "3213123"
    * он заполняет поле "Телефон" значением "3213123"
    * он нажимает кнопку "Открыть счет"
    #* на странице отображается текст "fname: Name can be specified with Latin characters only (a-Z) "
    * на странице отображается текст "lname: Surname can be specified with Latin characters only (a-Z)"
    * на странице отображается текст "phone: The phone number must be 10 digits"
    * атрибут "class" элемента "Открыть счет" содержит значение "enabled_false"

  @ChangeLanguage
  Сценарий: Смена языка. Чек-бокс документы на английском
    Дано пользователь открывает страницу Webtrader
    * пользователь находится на странице "Логин"
    * пользователь нажимает кнопку "Принять куки"
    * он нажимает кнопку "Регистрация"
    Дано пользователь находится на странице "Регистрация"
    * он выбирает язык "Русский" из выпадающего списка "Язык"
    * пользователь находится на странице "Логин"
    * на странице отображается текст "Авторизация"
    * он нажимает кнопку "Регистрация"
    Дано пользователь находится на странице "Регистрация"
    * элемент "Уже 18" кликабелен
    * элемент "Условия использования cookies" кликабелен
    * элемент "Согласие на доки на Английском" кликабелен