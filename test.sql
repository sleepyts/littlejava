SELECT name from
stu
GROUP BY name
HAVING min(score) > 60