package com.example.fabrica.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

public class ValidaData {

    //Teste do método abaixo isValidDate


    /**
     * Método que valida data com ou sem hora.
     * Verifica datas inválidas, horas inválidas, ano bissexto.
     * @param object Aceita um parâmetro a ser validado que pode ser String ou Date.
     */
    public static boolean isValidDate(Object object, boolean obrigatorio) {

        //Verifica se o objeto passado é string ou java.util.Date
        //Preenche a string a ser usada na validação
        String dataString = null;
        if(object instanceof String){
            dataString = object.toString();
        } else if (object instanceof Date) {
            SimpleDateFormat out = null;
            out = new SimpleDateFormat("ddMMyyyy");
            dataString = out.format(object);

        }

        //Verifica se é obrigatório e o campo não foi preenchido
        if(obrigatorio && isEmpty(dataString)){
            return false;
        } else
            //Verifica se não for obrigatório e o campo não foi preenchido
            if (!obrigatorio && isEmpty(dataString)){
                return true;
            }

        //Retira todos os caracteres que não forem numéricos
        dataString = dataString.replaceAll( "[^\\d]", "" );

        //Se campo possui hora, deve possuir pelo menos 14 dígitos

        //Se campo não possui hora, deve possuir pelo menos 8 digitos
        if(dataString.length() < 8){
            return false;
        }

        //Pega os valores do dia, mês e ano
        long dia = Long.parseLong(dataString.substring(0,2));
        long mes = Long.parseLong(dataString.substring(2,4));
        long ano = Long.parseLong(dataString.substring(4,8));

        Long hora = null;
        Long minuto = null;
        Long segundo = null;

        //Se data tem hora pega os valores de horas, minutos e segundos.

        try{
            //Preenche um objeto GregorianCalendar onde a validação acontece
            //Inclusive sobre ano bisexto, dia inválido, hora inválida
            GregorianCalendar data = new GregorianCalendar();
            data.setLenient(false);
            data.set(GregorianCalendar.YEAR, (int) ano);
            data.set(GregorianCalendar.MONTH, (int) mes -1);
            data.set(GregorianCalendar.DATE, (int) dia);


            // A validação da data ocorre aqui
            // Caso tenha alguma coisa errada com a data o sistema lança exceção
            // Capturada pelo catch abaixo e retorna false
            data.getTime();
        }
        catch(Exception e){
            return false;
        }
        return true;

    }

    //Método para verificar se string é vazia
    public static boolean isEmpty(String s) {
        return ((s == null) || ("".equals(s.trim())));
    }
}
