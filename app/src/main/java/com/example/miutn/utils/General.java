package com.example.miutn.utils;

import com.example.miutn.network.models.FechasExamenes;
import com.example.miutn.network.models.NMateria;
import com.example.miutn.network.models.Perfil;
import com.example.miutn.network.models.Temario;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class General {
    public static Perfil perfil;
    public static ArrayList<FechasExamenes> fechasExamenes;
    public static ArrayList<Temario> recomendaciones;

    public static final String canalNotificaciones="MiUTN";
    public static final CharSequence nombreCanal = "MiUtn_Canal";
    public static final String canalDescripcion="Sistema notificaciones MiUTN";
    public static  ArrayList<NMateria> puedoCursarNuevaVersion(ArrayList<NMateria> programaAnalitico, ArrayList<NMateria> misMaterias){
        ArrayList<NMateria> salida=new ArrayList<>();
        /*for(NMateria materiaRequisito : programaAnalitico) {
            if (materiaRequisito.getCorrelativas().contains(misMaterias)) {
                salida.add(materiaRequisito.getName());
        }*/
        for(NMateria materia : programaAnalitico){
            for(NMateria materiaCurse : misMaterias){
                if(CompararArray(materia.getCorrelativas(),materiaCurse.getCorrelativas())){
                    salida.add(materia);    //-->   Si la añado aca significa que puedo cursarla    <--
                }

            }
            if(materia.getCorrelativas()==null||materia.getCorrelativas().size()==0){
                salida.add(materia);
            }
        }
        return salida;
    }
    private static boolean CompararArray(ArrayList<String> lista1, ArrayList<String> lista2){
        if (lista1.size() != lista2.size()) {
            return false;
        }
        for (int i = 0; i < lista1.size(); i++) {
            if (!lista1.get(i).equals(lista2.get(i))) {
                return false;
            }
        }
        return true;
    }
    public static NMateria chipSeleccionadas(ChipGroup chipGroup){
        NMateria salida=new NMateria();
        for(Integer i:chipGroup.getCheckedChipIds()){
            Chip checkedChip = chipGroup.findViewById(i);
            if(checkedChip!=null){
                salida.setId(String.valueOf(checkedChip.getId()));
                salida.setName(String.valueOf(checkedChip.getText()));
            }
        }
        return salida;
    }
}
