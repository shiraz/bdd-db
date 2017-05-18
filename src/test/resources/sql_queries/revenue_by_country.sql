SELECT SUM(payment.amount) As total_gross, country.country, country.country_id
FROM customer
INNER JOIN address ON customer.address_id = address.address_id
INNER JOIN payment ON customer.customer_id = payment.customer_id
INNER JOIN city ON address.city_id = city.city_id
INNER JOIN country ON city.country_id = country.country_id
GROUP BY country.country, country.country_id
ORDER BY total_gross DESC