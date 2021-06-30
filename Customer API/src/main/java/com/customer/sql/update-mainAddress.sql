UPDATE address SET  state = :state,
					city = :city,
					neighborhood = :neighborhood,
					zipCode = :zipCode,
					street = :street,
					number = :number,
					additionalInformation = :additionalInformation,
					main = :main
WHERE				customer_id = :customer_id and 
					main = :main