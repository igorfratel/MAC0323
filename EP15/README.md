Objetivos
A gigante de alimentos CBS (Coelho, Barros & Silva) é uma das empresas mais respeitadas do Brasil. Visando contribuir com o desenvolvimento econômico e social do país, a CBS também realiza doações para políticos de diversas cidades nos quais a empresa acredita que possam ajudar nesta missão. Contudo, alguns representantes de instituições de fiscalização possuem a intenção de prejudicar os negócios da CBS, visando seus próprios interesses. 

A sua tarefa é auxiliar a diretoria da CBS a marcar encontros seguros com as autoridades para garantir sucesso nas negociações da empresa. 

Tarefa
A malha aeroviária brasileira é composta de N cidades com aeroporto, com M vôos entre elas. Cada vôo tem uma duração diferente, em unidades de tempo. Existem K executivos da CBS em cidades variadas do país.

Os executivos gostariam de se reunir nas cidades para fechar acordos com os políticos locais. Para que um acordo seja fechado, todos os executivos devem estar presentes.

Porém, existe uma equipe do Ministério Público que patrulha os aeroportos do país. Se a equipe encontrar algum executivo (ou grupo de executivos) da CBS antes que ele se reúna com o resto da diretoria, ele será preso e negociará uma delação premiada que colocará em risco o futuro da empresa.

Por isso, seu trabalho é identificar quais cidades são seguras para reuniões, ou seja, cidades em que todos os executivos conseguem chegar sem ser interceptados pelo MP independentemente de como a equipe se mova para procurá-los. Se o MP chegar a uma cidade junto com todos os executivos, todos irão em custódia. Se os executivos chegarem a uma cidade antes do MP (mesmo que seja 1 unidade de tempo), eles conseguirão fechar negócio e desaparecer da cidade antes de serem incomodados.

Especificações
Vocês deverão preparar um arquivo chamado CBSPaths.java. Dessa vez, não teremos métodos a serem preparados - o programa de vocês será testado via entrada e saída padrão. Ou seja, deve ter uma main() que chame outros métodos privados (que ficam a seu critério definir).

Vocês receberão no stdin um arquivo cuja primeira linha contém os valores de N, M e K separados por espaços. Relembrando, N é o número de cidades, M é o número de vôos entre as cidades e K é o número de executivos da CBS.

As próximas M linhas representarão os vôos e serão no formato A B T,  sendo A a cidade de saída do vôo, B a cidade de chegada e T a duração dele. As cidades são representadas pelo código IATA de seus aeroportos (3 letras). Lembre-se que os vôos são de mão única e não necessariamente existe um vôo de B pra A, só se ele também for especificado. Ele também pode ter uma duração diferente do vôo de A pra B por motivos de correntes de ar.

As próximas K linhas terão apenas a cidade inicial de cada executivo (mais uma vez representada pelo código IATA).

A última linha terá a cidade inicial da equipe do Ministério Público (IATA de novo).

Na saída, vocês deverão imprimir os códigos IATA de todas as cidades seguras para reuniões com as lideranças locais, um por linha, em ordem alfabética. Caso não haja nenhuma cidade segura, imprima apenas "VENHA COMIGO PARA CURITIBA".

Ah, e é garantindo que a malha aérea do input forma uma componente fortemente conexa. Você lembra o que é isso?