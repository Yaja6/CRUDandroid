package firebase.app.appprueba.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import firebase.app.appprueba.Modelos.Contacto;
import firebase.app.appprueba.R;

public class ListaContactosAdaptador extends BaseAdapter {

    Context context;
    ArrayList<Contacto> contactoData;
    LayoutInflater layoutInflater;
    Contacto contactoModel;


    public ListaContactosAdaptador(Context context, ArrayList<Contacto> contactoData) {
        this.context = context;
        this.contactoData = contactoData;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    @Override
    public int getCount() {
        return contactoData.size();
    }
    @Override
    public Object getItem(int position) {
        return contactoData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null){
            rowView = layoutInflater.inflate(R.layout.lista_contactos, null, true);
        }
        //Enlazar vistas desde listas_contactos
        TextView nombre = rowView.findViewById(R.id.nombre);
        TextView telefono = rowView.findViewById(R.id.telefono);
        TextView fechaReg = rowView.findViewById(R.id.fechaReg);

        contactoModel = contactoData.get(position);
        nombre.setText(contactoModel.getNombre());
        telefono.setText(contactoModel.getTelefono());
        fechaReg.setText(contactoModel.getFecha());

        return rowView;
    }
}
