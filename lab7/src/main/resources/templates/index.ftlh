<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Веб-панель Автобази</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background-color: #f4f4f9; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #28a100; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        .form-box { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 500px; }
        .btn-delete { color: red; text-decoration: none; font-weight: bold; }
        input, select { width: 100%; padding: 8px; margin: 8px 0; box-sizing: border-box; }
        button { background-color: #28a100; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer; }
    </style>
</head>
<body>

    <h1> Система управління Автобазою (Spring Boot + MVC)</h1>

    <h2>Додати нового водія у рейс</h2>
    <div class="form-box">
        <form action="/add-driver" method="post">
            <label>ПІБ Водія:</label>
            <input type="text" name="name" placeholder="Прізвище Ім'я" required>

            <label>Пункт призначення рейсу:</label>
            <input type="text" name="destination" placeholder="Місто-Місто" required>

            <label>Вага вантажу (тонн):</label>
            <input type="number" name="weight" min="1" required>

            <label>Тип вантажу (Паттерн Strategy):</label>
            <select name="cargoType">
                <option value="standard">Стандартний вантаж (5000 грн за 1 Т.)</option>
                <option value="fragile">Крихкий / Небезпечний (8500 грн за 1 Т.)</option>
            </select>

            <button type="submit">Оформити водія та рейс</button>
        </form>
    </div>

    <h2>Поточний список водіїв та їхніх рейсів у БД</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Ім'я водія</th>
                <th>Маршрути (Пункт призначення)</th>
                <th>Вага вантажу</th>
                <th>Розрахована вартість (UAH)</th>
                <th>Дії</th>
            </tr>
        </thead>
        <tbody>
            <#list drivers as driver>
                <tr>
                    <td>${driver.id}</td>
                    <td><strong>${driver.name}</strong></td>
                    <td>
                        <#list driver.trips as trip>
                            ${trip.destination}<#sep>,
                        </#list>
                    </td>
                    <td>
                        <#list driver.trips as trip>
                            ${trip.weight} т.<#sep>,
                        </#list>
                    </td>
                    <td>
                        <#list driver.trips as trip>
                            ${trip.price} грн<#sep>,
                        </#list>
                    </td>
                    <td>
                        <a class="btn-delete" href="/delete/${driver.id}">Видалити</a>
                    </td>
                </tr>
            <#else>
                <tr>
                    <td colspan="6" style="text-align: center; color: gray;">База даних автобази порожня. Оформіть першого водія вище!</td>
                </tr>
            </#list>
        </tbody>
    </table>

</body>
</html>