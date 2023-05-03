DELETE FROM employer_contact
WHERE EMPLOYER_ID IN (
    SELECT ID FROM employer
    WHERE SEARCH_VALUE IN (
      'ВЕРХНЬОТЕСТІЇВСЬКА МІСЬКА ЛІКАРНЯ №1',
      'ЗАВОД "МЕТЕОР"',
      'КАФЕ "ДНІПРОВСЬКА ХВИЛЯ"',
      'САЛОН КРАСИ "ЛІЛЕЯ"',
      'ВИРОБНИЧЕ ОБ''ЄДНАННЯ "ПРОТОН-СОКІЛ"'
    )
);

DELETE FROM employer
WHERE SEARCH_VALUE IN (
  'ВЕРХНЬОТЕСТІЇВСЬКА МІСЬКА ЛІКАРНЯ №1',
  'ЗАВОД "МЕТЕОР"',
  'КАФЕ "ДНІПРОВСЬКА ХВИЛЯ"',
  'САЛОН КРАСИ "ЛІЛЕЯ"',
  'ВИРОБНИЧЕ ОБ''ЄДНАННЯ "ПРОТОН-СОКІЛ"'
);