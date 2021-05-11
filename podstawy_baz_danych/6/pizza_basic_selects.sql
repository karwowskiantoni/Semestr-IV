/* alfabetycznie pizze */
SELECT * FROM MENU 
	ORDER BY pizza ASC;
	
/* ceny malejąco nazwy rosnąco*/
SELECT * FROM MENU
	ORDER BY price DESC, pizza ASC;

/* wszystkie ceny bez duplikatów */
SELECT DISTINCT price FROM MENU;

/* włoskie pitce taniocha */
SELECT * FROM MENU
	WHERE country='Italy' AND price < 7.00;
	
/* pitce nie z włoch i usa*/
SELECT * FROM MENU
	WHERE (country!='Italy' AND country!='U.S.') OR country IS NULL;
	
/* vegetariano americano mexicano garlicano */
SELECT * FROM MENU
	WHERE pizza='americano' OR pizza='mexicano' OR pizza='garlic' OR pizza='vegetarian';

/* pitca miedzy 6, a 7 */
SELECT * FROM MENU
	WHERE price >= 6.00 AND price <= 7.00;
	
/* pitca co sie ano konczy */
SELECT * FROM MENU
	WHERE pizza LIKE '%ano';
	
/* pitce gdzie nie ma nula */	
SELECT * FROM MENU
	WHERE country IS NOT NULL;
	
/* przyprawy szachermacher */
SELECT DISTINCT amount FROM RECIPES
	WHERE ingredient = 'spice'
	ORDER BY amount DESC;	
	
