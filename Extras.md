# Puntos Extras

- Dar alguna solución sobre qué pasa con una llamada cuando no hay ningún empleado libre.
- Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes.

Se utilizó un "PriorityBlockingQueue", para sacar a los empleados disponibles de una queue priorizada, y así garantizar que los primeros en atender una llamada sean los Operadores, luego los Supervisores y por último los Directores. Ademas, si no hay empleados en la queue (cola), el metodo "TAKE" bloquea el flujo hasta que algun empleado vuleva a la queue (cola) para continuar el flujo.

Siempre que haya una empleado disponible, se atiende la llamada, sino esta quedará a la espera de que algun empleado vuelva a estar disponible.
