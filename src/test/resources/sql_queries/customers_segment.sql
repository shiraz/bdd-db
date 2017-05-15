SELECT customer.customer_id, customer.first_name, customer.last_name, customer.email, city.city, country.country, SUM(payment.amount) As total_amount_paid FROM customer
INNER JOIN  address ON  customer.address_id = address.address_id
INNER JOIN payment ON customer.customer_id = payment.customer_id
INNER JOIN city ON address.city_id = city.city_id
INNER JOIN country ON city.country_id = country.country_id
WHERE country.country = 'United States'
GROUP BY customer.customer_id, address.city_id, city.city, country.country
HAVING SUM(payment.amount) <= (
SELECT AVG(total_sum) FROM (
SELECT c.customer_id, SUM(p.amount) AS total_sum
FROM customer c INNER JOIN payment p ON c.customer_id = p.customer_id
GROUP BY c.customer_id
) as c2
)
ORDER BY SUM(payment.amount) DESC