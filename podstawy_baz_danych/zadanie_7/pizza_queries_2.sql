USE pizza;

/* ZADANIE 1 */

--1
SELECT COUNT(*) AS total_pizzas
FROM MENU;

--2
SELECT COUNT(distinct countr) AS countries
FROM MENU;

--3
SELECT MIN(price) AS cheapest_italian
FROM MENU
WHERE country='Italy';

--4
SELECT MIN(price) AS min_price, MAX(price) AS max_price, AVG(price) AS avg_price
FROM MENU;

--5
SELECT COUNT(base) AS no_of_wholemeal
FROM MENU
WHERE base='wholemeal';

--6
SELECT COUNT(pizza) AS no_motherland
FROM MENU
WHERE country IS NULL;

--7
SELECT ROUND(AVG(price)*50*0.3,2) AS income
FROM MENU;

/* ZADANIE 2 */

--1
SELECT ingredient,  type
FROM ITEMS
LEFT JOIN RECIPES
ON ITEMS.ingredient = RECIPES.ingredient
WHERE RECIPES.pizza = 'margherita';

--2
SELECT ITEMS.ingredient, RECIPES.pizza
FROM ITEMS
LEFT JOIN RECIPES
ON ITEMS.ingredient = RECIPES.ingredient
WHERE ITEMS.type = 'fish' AND RECIPES.pizza IS NOT NULL;

--3
SELECT ITEMS.ingredient, RECIPES.pizza
FROM ITEMS
INNER JOIN RECIPES
ON ITEMS.ingredient = RECIPES.ingredient
WHERE ITEMS.type = 'meat' AND RECIPES.pizza IS NOT NULL;

--4
SELECT M2.pizza
FROM MENU M1 JOIN MENU M2
ON M1.country = M2.country WHERE M1.pizza = 'siciliano' AND M2.pizza != 'siciliano';


--5
SELECT M2.pizza, M2.price
FROM MENU M1 JOIN MENU M2
ON M1.price < M2.price WHERE M1.pizza = 'quattro stagioni';

--6
SELECT ITEMS.ingredient, RECIPES.pizza
FROM ITEMS
LEFT JOIN RECIPES
ON ITEMS.ingredient = RECIPES.ingredient
WHERE ITEMS.type = 'fish'
ORDER BY ingredient ASC;

--7
SELECT DISTINCT ITEMS.type
FROM ITEMS
LEFT JOIN RECIPES
ON RECIPES.ingredient = ITEMS.ingredient
LEFT JOIN MENU
ON RECIPES.pizza = MENU.pizza
WHERE MENU.country = 'U.S.' OR MENU.country = 'Mexico' OR MENU.country = 'Canada';

--8
SELECT DISTINCT RECIPES.pizza
FROM RECIPES
LEFT JOIN ITEMS
ON RECIPES.ingredient = ITEMS.ingredient
LEFT JOIN MENU
ON RECIPES.pizza = MENU.pizza
WHERE ITEMS.type = 'fruit' AND MENU.base = 'wholemeal'




select ingredient, pizza, amount from recipes
where 
select max(amount), ingredient from recipes group by ingredient







