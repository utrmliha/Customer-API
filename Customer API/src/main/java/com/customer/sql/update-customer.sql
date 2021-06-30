UPDATE customer SET name = :name,
					email = :email,
					birthDate = :birthDate,
					cpf = :cpf,
					gender = :gender,
					updateAt = :updateAt
WHERE 
					id =  :id