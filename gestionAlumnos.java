package AlumnosArica;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class gestionAlumnos {

	static String [][] baseDeDatos = new String[20][13];
	static String [] menu = new String [7];
	static int cupos = 16;  //idealmente dynamico
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		menu[0]="rut";
		menu[1]="nombre";
		menu[2]="apellidoP";
		menu[3]="apellidoM";
		menu[4]="contacto";
		menu[5]="correo";
		menu[6]="fechaNac";
		
		String opcion,rut,nombre,apellidoM,apellidoP,contacto,correo,fechaNac;
		int n1,n2,n3,n4,n5,n6;
		boolean completadoIngreso, valido;
				
		opcion = "0";
		
		
		
		inicializarBBDD();
		
		while (!opcion.equals("4")){
				System.out.println("---------------------------");
				System.out.println("            MENU           ");
				System.out.println("---------------------------");
				System.out.println("Elija una opcion");
				System.out.println("1.- Ingresar alumno nuevo");
				System.out.println("2.- Ver Registro de alumnos");
				System.out.println("3.- Buscar Registro ");
				System.out.println("    - Actualizar Registro");
				System.out.println("    - Eliminar Registro");
				System.out.println("    - Generar Reporte");
				System.out.println("4.- Salir");
				
				opcion = sc.nextLine();
				
// SUB PROCESO POR DESAROLLAR
				//valido = soloNumeros(opcion);				
				valido = true;
				
				if (!valido) {
					opcion = "5";
				}
				
				
				switch (opcion) {				
					case "1": 
						borrarPantalla();
						if(hayCupos()==-1) {
							System.out.println("No quedan cupos");
						}else {
// SUB PROCESO POR DESAROLLAR
							ingresarAlumno();
							cupos--;
						}
						break;
						
					case "2":
						borrarPantalla();
						System.out.println("Ver registro de Alumnos");
						muestra();
						break;
							
					case "3":
						borrarPantalla();
// SUB PROCESO POR DESAROLLAR
						buscarPorNombre(menu);
						
						break;
						
					case "4":
						borrarPantalla();
						System.out.println("Adios");	
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
						
					default:
						borrarPantalla();
						System.out.println("Opcion no Valida");
					
				}				
		}	
	}

	
	public static void ingresarAlumno() {
		int cupos=0;
		String aux="";
		int dia,mes,ano;
		for (int i=0; i < baseDeDatos.length; i++) {
			if(baseDeDatos[i][0]=="") {
				cupos=cupos+1;
				System.out.println("Fila vacia"+i);
				for(int j=0; j<menu.length; j++) {
					System.out.println("Ingrese "+menu[j]);
					if(menu[j]!="fechaNac") {
						aux=sc.nextLine();
					}
					if(menu[j]=="nombre"||menu[j]=="apellidoP"||menu[j]=="apellidoM") {
						System.out.println(soloLetras(aux));
						if(soloLetras(aux)==false) {
							System.out.println("Valor no valido, porfavor reingrese");
							j=j-1;
						}else {
							baseDeDatos[i][j]=aux;
						}
						
					}else if(menu[j]=="rut") {
						if(validaRut(aux)==false) {
							System.out.println("Rut invalido");
							j=j-1;
						}else {
							baseDeDatos[i][j]=aux;
						}
					}else if(menu[j]=="fechaNac") {
						System.out.println("Ingrese el dia");
						dia=sc.nextInt();
						System.out.println("Ingrese el mes");
						mes=sc.nextInt();
						System.out.println("Ingrese el año");
						ano=sc.nextInt();
						if(validaFecha(dia,mes,ano)==false) {
							System.out.println("Fecha ingresada es invalida");
							j=j-1;
							
						}else {
							baseDeDatos[i][j]=dia+"/"+mes+"/"+ano;
						}
					}else if(menu[j]=="contacto") {
						if(validaNum(aux)==false) {
							System.out.println("Numero de contacto no valido, reingrese");
							j=j-1;
						}else {
							if(validaLargo(aux)==false) {
								System.out.println("Numero de contacto no valido, reingrese");
								j=j-1;
							}else {
								baseDeDatos[i][j]=aux;
							}
							
						
						}
					}else if(menu[j]=="correo") {
						if(validarCorreo(aux)==false) {
							System.out.println("Correo invalido, reingrese");
							j=j-1;
						}else {
							baseDeDatos[i][j]=aux;
						}
					}
					
					else {
						baseDeDatos[i][j]=aux;
					}
				}
				i=100;
			}else {

			}
			}
		if(cupos==0) {
			System.out.println("No hay cupos disponibles");
		}
		
	}
	
	public static void buscarPorNombre (String[] menu) {
		int alumnoSeleccionado, opcionSubmenu, opcionModificar, notaModificar, encontrado;
		int[] indiceDatosEncontrados = new int[20];
		double promedio;
		boolean salir, salirNotas, valido;
		String datos, confirmar, nota, notaValidada, enter, aux;
		
		System.out.println("---------------------------");
		System.out.println("     BUSQUEDA DE ALUMNO    ");
		System.out.println("---------------------------");
		
		salir = false;
		while (!salir) {
			System.out.println("Cual dato del alumno tiene?");
			System.out.println("ingrese el numero correspondiente" );
			System.out.println("1.-RUT");
			System.out.println("2.-Nombre");
			System.out.println("3.-Apellido Paterno");
			System.out.println("4.-Apellido Materno");
			System.out.println("5.-Volver al menu principal");
			
			aux = sc.nextLine();
			valido = soloNumeros(aux);
// SUB PROCESO POR DESAROLLAR
			valido= true; //para q funcione
			encontrado = 0; //cuantos alumnos se encontraron con el dato ingresado
			
			//pide la variable a buscar en la BBDD para identificar alumo/s
			if (!valido) {
				borrarPantalla();
				System.out.println("Opcion no valida");
				System.out.println("---------------------------");
			}else {
				//alumnoSeleccionado = convertirANumero(aux)
				alumnoSeleccionado = Integer.parseInt(aux);
				if(alumnoSeleccionado == 5) {
					borrarPantalla();
					System.out.println("Volver al menu");
					System.out.println("---------------------------");
					salir = true;
				}else { //opcion 1-4
					borrarPantalla();
					System.out.println("Ingrese el "+menu[alumnoSeleccionado-1]+" del alumno");
					
					datos = sc.nextLine();
					borrarPantalla();
					
					for (int i=0; i<20;i++) {
						if (baseDeDatos[i][alumnoSeleccionado-1].equals(datos)) {
							System.out.println("Alumno "+ encontrado);
							indiceDatosEncontrados[encontrado] = i;
							encontrado++;
							for (int j=0;j<7;j++) {
								if(!baseDeDatos[i][j].equals("")) { //si no esta vacio
									System.out.print(menu[j]+": " );
									System.out.print(baseDeDatos[i][j]+"\n");
								}
							}
							System.out.println("---");
						}
					}//fin for BBDD
					borrarPantalla();
					
					if (encontrado==0) {
						borrarPantalla();
						System.out.println("Registro no encontrado");
						System.out.println("---------------------------");
					}else {
						salir = true;
						if (encontrado == 1) {
							alumnoSeleccionado = 1;
						}else {
							System.out.println("Seleccione el alumno que corresponde");
							for(int i= 0; i<encontrado;i++) {
								int temp = i+1;
								System.out.println("Alumno "+temp+".- "+ baseDeDatos[indiceDatosEncontrados[i]][0]+ " " + baseDeDatos[indiceDatosEncontrados[i]][1] );
							}
							aux = sc.nextLine();
							valido = soloNumeros(aux);
							if (!valido) {
								borrarPantalla();
								System.out.println("Opcion no valida");
								System.out.println("---------------------------");
							}else {
								alumnoSeleccionado = Integer.parseInt(aux);
							}
							borrarPantalla();
						}
						if (encontrado!=0 && valido) {
							subMenuAlumno(indiceDatosEncontrados,alumnoSeleccionado);
						}
					}
					
				}//fin else !=5
			}//fin else valido		
		}
	}
	
	public static String dv ( String rut ) {
		Integer M=0,S=1,T=Integer.parseInt(rut);
		for (;T!=0;T=(int) Math.floor(T/=10))
			S=(S+T%10*(9-M++%6))%11;
		return ( S > 0 ) ? String.valueOf(S-1) : "k";		
	}
	
	public static void subMenuAlumno(int[] indiceDatosEncontrados, int alumnoSeleccionado){
		int opcionSubMenu=0;
		double promedio=0;
		int opcionModificar = 0;
		int indice,notaModificar,nota;
		String enter,aux,notaValidada,confirmar;
		boolean salirNotas,valido;
		
		notaModificar=-1;
		nota =0; //para q funcione, puede que tiene que ser -1 o otra cosa
		
		indice = indiceDatosEncontrados[alumnoSeleccionado-1];
		while(opcionSubMenu!=6) {
			System.out.println("---------------------------");
			System.out.println("      MENU DE ALUMNOS      ");
			System.out.println("---------------------------");
						
			for(int i =0;i<7;i++) {				
				System.out.println(menu[i] + ": " + baseDeDatos[indice][i]);
			}
			
			System.out.println("---------------------------");
			System.out.println("Elija una opcion");
			System.out.println("1.- VER NOTAS");
			System.out.println("2.- INGRESAR O MODIFICAR NOTAS");
			System.out.println("3.- MODIFICAR REGISTRO");
			System.out.println("4.- Eliminar Registro");
			System.out.println("5.- Generar Reporte de alumno");
			System.out.println("6.- VOLVER AL MENU PRINCIPAL");
			
			opcionSubMenu = sc.nextInt();
			
			switch(opcionSubMenu) {
			
			case 1: //VER NOTAS
				borrarPantalla();
				System.out.println("LISTA DE NOTAS");
// SUB PROCESO POR DESAROLLAR
				escribirNotas();
				System.out.println("Ingrese enter para volver al menu Principal");
				enter = sc.nextLine();
				while(!enter.equals("")){
					enter = sc.nextLine();
				}
				enter = sc.nextLine();
			break;
			
			case 2: //INGRESAR O MODIFICAR NOTAS
				borrarPantalla();
				salirNotas=false;
				while(!salirNotas) {
					System.out.println("Notas Actuales");
					escribirNotas();
					
					System.out.println("0.- Volver al menu de alumno");
					System.out.println("Ingrese numero de nota a modificar");
					
					aux = sc.nextLine();
					valido = soloNumeros(aux);
					if(!valido) {
						borrarPantalla();
						System.out.println("Opcion no valida");
						System.out.println("---------------------------");
					}else {
						notaModificar = Integer.parseInt(aux);
					}
					
					if(notaModificar == 0) {
						salirNotas =true;
						borrarPantalla();
					}else {
						if(notaModificar!=0 && valido) {
							if(notaModificar<=6) {
								System.out.println("Nota "+notaModificar+": "+ baseDeDatos[indice][notaModificar+6]);
								System.out.println("Ingrese Valor");
								aux = sc.nextLine();
								valido = soloNumeros(aux); 
								if(!valido) {
									borrarPantalla();
									System.out.println("Opcion no valida");
									System.out.println("---------------------------");
								}else {
									nota = Integer.parseInt(aux);
								}
								notaValidada = Integer.toString(notaValida(nota));
								
								if (notaValidada.equals("-1")) {
									borrarPantalla();
									System.out.println("Nota no valida");	
									System.out.println("---------------------------");
								}else {
									nota = Integer.parseInt(notaValidada);
									System.out.println("Nota valida");	
									borrarPantalla();
									baseDeDatos[indice][notaModificar+6] = Integer.toString(nota);
								}
							}else {
								borrarPantalla();
								System.out.println("Opcion no valida");
								System.out.println("---------------------------");
							}
						}else {
							borrarPantalla();
							System.out.println("Opcion no valida");
							System.out.println("---------------------------");
						}
					}
				}//fin While
			break;
			case 3: //modificar REGISTRO
				borrarPantalla();
				System.out.println("Modificar Registro de Alumno");
				int temp = 0;
				for (int i=0; i<7;i++) {
					temp = i+1;
					System.out.println(temp+".- "+menu[i]+": "+baseDeDatos[indice][i]);
				}
				System.out.println("8 .-Volver al Registro del Alumno");
				System.out.println("Ingrese numero de datos a modificar");
				
				aux = sc.nextLine();
				valido = soloNumeros(aux); 
				if(!valido) {
					borrarPantalla();
					System.out.println("Opcion no valida");
					System.out.println("---------------------------");
					opcionModificar=8;
				}else {
					opcionModificar = Integer.parseInt(aux);
				}
				
				borrarPantalla();
				if(opcionModificar !=8) {
					System.out.println(menu[opcionModificar-1]+ " actual: "+ baseDeDatos[indice][opcionModificar-1]);
					System.out.println("Ingrese Valor");
					baseDeDatos[indice][opcionModificar-1] = sc.nextLine();
					borrarPantalla();
				}
			break;
			case 4: //ELIMINAR REGISTRO
				borrarPantalla();
				System.out.println("Eliminar Registro");
				System.out.println("Estas Seguro (ingrese S para confirmar)");
				confirmar = sc.nextLine();
				if(confirmar.equals("s")) {
					for(int i = 0 ;i<13;i++) {
						baseDeDatos[indice][opcionModificar-1]="";						
					}
					borrarPantalla();
					System.out.println("Registro Eliminado");
					opcionSubMenu = 6;
				}else {
					borrarPantalla();
					System.out.println("el Registro no fue Eliminado");
				}
			break;
			case 5:
				borrarPantalla();
// SUB PROCESO POR DESAROLLAR
				generarReporte(indiceDatosEncontrados[alumnoSeleccionado-1]);
				opcionSubMenu = 6;
			break;
			case 6:
				borrarPantalla();
				opcionSubMenu = 6;
			break;
			default: 
				System.out.println("Opcion no valida");
				System.out.println("---------------------------");
			}
		}
	}
	
	//Falta Desarollar version prueba de pseInt-> java
	public static void generarReporte( int indicealumno){
        boolean completado;
        int i;
        String profe;
        double promedio;
        completado = true;
        promedio = 0;
        System.out.println("Ingrese su nombre");
        profe = bufEntrada.readLine();
        System.out.println(""); // no hay forma directa de borrar la consola en Java
        System.out.println("REPORTE DE NOTAS");
        escribirnotas(baseDeDatos,indicealumno);
        // Calcular promedio (no truncado)
        for (i=7;i<=12;i++) {
            promedio = promedio+String.valueOf(baseDeDatos[indicealumno][i]);
        }
        promedio = promedio/6;
        if (String.valueOf(Double.toString(promedio).substring(3,4))>=5) {
            promedio = promedio+0.1;
        }
        promedio = String.valueOf(Double.toString(promedio).substring(0,3));
        if (completado==false) {
            System.out.println("El alumno no ha completado el curso");
        } else {
            if (promedio>=4) {
                System.out.println("El alumno aprobo con un");
            } else {
                System.out.println("El alumno reprobo con un ");
            }
            // covertir promedio a texto
            // subproceso
            numatexto(promedio);
        }
        System.out.println("");
        System.out.println("Acreditado por "+profe);
        System.out.println("");
        System.out.println("Ingrese ENTER para volver al menu principal");
        while (!profe.equals("")) {
            profe = bufEntrada.readLine();
        }
        System.out.println(""); // no hay forma directa de borrar la consola en Java
    }
	//Falta Desarollar version prueba de pseInt-> java
	public static void numatexto(double promedio) {
        String cad;
        int i;
        int num;
        String unidad[];
        unidad = new String[10];
        unidad[0] = "Cero";
        unidad[1] = "Uno";
        unidad[2] = "Dos";
        unidad[3] = "Tres";
        unidad[4] = "Cuatro";
        unidad[5] = "Cinco";
        unidad[6] = "Seis";
        unidad[7] = "Siete";
        unidad[8] = "Ocho";
        unidad[9] = "Nueve";
        if (Double.toString(promedio).length()>1) {
            cad = Double.toString(promedio).substring(0,1);
            System.out.print(unidad[Integer.parseInt(cad)]);
            System.out.print(" coma ");
            cad = Double.toString(promedio).substring(2,3);
            System.out.print(unidad[Integer.parseInt(cad)]);
        } else {
            cad = Double.toString(promedio).substring(0,1);
            System.out.print(unidad[Integer.parseInt(cad)]+" coma cero");
        }
    }
	//Falta Desarollar version prueba de pseInt-> java
	public static void escribirNotas () {
		
	}
	
	public static boolean validaNum(String num) {
		for (int i=0; i<num.length();i++) {
			char c=num.charAt(i);
			if (c =='0'||c =='1'||c =='2'||c =='3'||c =='4'||c =='5'||c =='6'||c =='7'||c =='8'||c =='9') {
				
			}else {
				return false;
			}
		}
		return true;
	}
	
	public static boolean validaLargo(String num) {
		if(num.length()<9||num.length()>9) {
			return false;
		}
		
		return true;
	}
	
	public static boolean validaFecha(int dia, int mes,int ano) {
		if(dia>31 ||dia<1) {
			return false;
		}else if(mes>12 || mes<0) {
			return false;
		}else if(ano>5000||ano<1000) {
			return false;
		}
		return true;
		
	}
    
	public static boolean soloLetras(String cadena) {
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                return false;
            }
        }
        return true;
    }
        
	public static Boolean validaRut ( String rut ) {
		Pattern pattern = Pattern.compile("^[0-9]+-[0-9kK]{1}$");
		Matcher matcher = pattern.matcher(rut);
		if ( matcher.matches() == false ) return false;
		String[] stringRut = rut.split("-");
		return stringRut[1].toLowerCase().equals(dv(stringRut[0]));
	}
	
	
	public static int notaValida (int nota) {
		return 1;
	}
	
	//MUESTRA LA BASE DE DATOS (TODOS LOS ALUMNOS, NO LAS NOTAS)
	public static void muestra () {
		System.out.println("---------------------------");
		System.out.println("     LISTA DE ALUMNOS      ");
		System.out.println("---------------------------");

		for(int i=0;i<20;i++) {
			if (baseDeDatos[i][0]!="") {
				for (int j=0;j<7;j++) {
					//System.out.println(i+" menu: "+menu[j]);
					System.out.println(baseDeDatos[i][j]);					
				}
			}
		}		
	}
	
	//REVISA SI HAY CUPOS DISPONIBLES EN BASE A CAMPOS DE RUT EN BLANCO
	// REVISAR SI ES Q HAY CUPOS DISPONIBLES PARA AGREGAR ALUMNOS
	public static int hayCupos() {		
		for (int i =0; i<20;i++) {
			if (baseDeDatos[i][0]==null) {
				System.out.println("hay cupos");
				return 1;
			}
		}
		return -1;		
	}
	
	//INICIALIZA BASE DE DATOS EN BLANCO Y CON DATOS DE PRUEBA
	//INICIALIZA LA BASE DE DATOS CON ESPACIO EN BLANCO (NULL?) Y 0.0 PARA LAS NOTAS
	public static void inicializarBBDD() {
		
		//inicializar la base de datos en blanco y en zero para las notas
		for(int i =0; i<20;i++) {
			for (int j =0;j<13;j++) {
				baseDeDatos[i][j] ="";
				if (j>6) {
					baseDeDatos[i][j] ="0.0";
				}
			}
		}
		
		//Data de prueba
		
		baseDeDatos[0][0]= "8.6-7";
		baseDeDatos[0][1]= "Karina";
		baseDeDatos[0][2]= "joas";
		baseDeDatos[0][3]= "toop";
		baseDeDatos[0][4]= "+56978124598";
		baseDeDatos[0][7]= "5.8";
		baseDeDatos[0][8]= "1.9";
		baseDeDatos[0][9]= "6.9";
		baseDeDatos[0][10]= "2.9";
		baseDeDatos[0][11]= "2.9";
		baseDeDatos[0][12]= "3.5";
		
		baseDeDatos[1][0]= "14.538.658-9";
		baseDeDatos[1][1]= "Karina";
		baseDeDatos[1][2]= "kiit";
		baseDeDatos[1][3]= "lwwe";
		baseDeDatos[1][4]= "+56978135798";
		baseDeDatos[1][7]= "7.0";
		baseDeDatos[1][8]= "4.1";
		
		baseDeDatos[2][0]= "12.687.251-k";
		baseDeDatos[2][1]= "Cristopher";
		baseDeDatos[2][2]= "aeew";
		baseDeDatos[2][3]= "yttwq";
		baseDeDatos[2][4]= "+5697841254";
		baseDeDatos[2][7]= "6.2";
		baseDeDatos[2][8]= "4.6";
		
		baseDeDatos[3][0]= "9.688.653-k";
		baseDeDatos[3][1]= "XÆA-12";  //alt 146 = Æ
		baseDeDatos[3][2]= "musk";
		baseDeDatos[3][3]= "grimes";
		baseDeDatos[3][4]= "+5697841254";
		baseDeDatos[3][7]= "7";
		baseDeDatos[3][8]= "7";
		
		
	}
	
	
	public static void borrarPantalla()	{  
		for (int i=0;i<20;i++) System.out.println();
		System.out.flush();
	}  
	
	public static boolean validarCorreo(String correo) {
		//Solo funciona con gmail.hotmail u outlook
	String aux="";
	String gmail="gmail.com";
	String hotmail="hotmail.com";
	String outlook="outlook.com";
		aux=correo.substring(correo.lastIndexOf('@') + 1);;
		if(aux.equals(gmail)||aux.equals(hotmail)||aux.equals(outlook)) {

		}else{
			return false;
		}
		return true;
	}
	
	//VALIDA QUE EL TEXTO INGRESADO SEA UN NUMERO DE 0-9
	public static boolean soloNumeros(String num) {
		for (int i=0; i<num.length();i++) {
			char c=num.charAt(i);
			if (c =='0'||c =='1'||c =='2'||c =='3'||c =='4'||c =='5'||c =='6'||c =='7'||c =='8'||c =='9') {
				
			}else {
				//Ideal poner aqui los system.out opcion no valida, etc. se repite mucho en el codigo
				return false;
			}
		}
		return true;
	}
	
}
