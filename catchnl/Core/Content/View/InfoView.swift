//
//  InfoView.swift
//  catchnl
//
//  Created by Fatih Toker on 15.09.2023.
//

import SwiftUI

struct InfoView: View {
    
    var body: some View {
        @EnvironmentObject var viewModel: ContentViewModel

        
        NavigationView {
            ScrollView(.vertical){
                VStack{
                    Text("Veilige wegen met CATCHNL")
                        .font(.custom(Font.Poppins_SemiBold, size: 25))
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .padding(.bottom, 1)
                        .padding(.top)
                    
                    Text("Meld onveilig gedrag met CATCHNL! Onze app stelt je in staat om eenvoudig en snel gevaarlijk rijgedrag te melden. Door jouw melding kan de verzekeringspremie van roekeloze weggebruikers stijgen en kun je zelfs eventueel korting verdienen op jouw premie.")
                        .foregroundColor(.white)
                        .multilineTextAlignment(.center)
                        .font(.custom(Font.Poppins_Regular, size: 16))
                        .padding(.horizontal)

                }
                .padding(.bottom)
                .background(Color("primary-color"))
                
                HStack(alignment: .center){
                    Image(systemName: "lock.fill")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 50)
                        .foregroundColor(Color("primary-color"))
                    VStack(alignment: .leading){
                        Text("Volledige anonimiteit")
                            .font(.custom(Font.Poppins_SemiBold, size: 20))

                        Text("Volledig anoniem melden van onveilig rijgedrag. Veiligheid en privacy zijn gegarandeerd voor gebruikers. Bijdragen aan een veiliger verkeer zonder zorgen over privacy.")
                            .font(.custom(Font.Poppins_Regular, size: 12))

                    }
                }
                .padding()
                
                HStack(alignment: .center){
                    Image(systemName: "star")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 50)
                        .foregroundColor(Color("primary-color"))
                    VStack(alignment: .leading){
                        Text("Veilig rijgedrag bevorderen")
                            .font(.custom(Font.Poppins_SemiBold, size: 20))

                        Text("Herhaaldelijke meldingen van weggebruikers kunnen leiden tot een stijging van de verzekeringspremie van de roekeloze rijder, wat verantwoordelijk rijgedrag bevordert en de verkeersveiligheid verbetert.")
                            .font(.custom(Font.Poppins_Regular, size: 12))

                    }
                }
                .padding()
                
                HStack(alignment: .center){
                    Image(systemName: "person.3")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 50)
                        .foregroundColor(Color("primary-color"))
                    VStack(alignment: .leading){
                        Text("Samen doen")
                            .font(.custom(Font.Poppins_SemiBold, size: 20))

                        Text("Samen veiliger rijden. Meld onveilig rijgedrag, verlicht de autoriteiten. Veiligheid voor iedereen. Doe mee!")
                            .font(.custom(Font.Poppins_Regular, size: 12))

                    }
                }
                .padding()
                
                Spacer()
                
                
            }
            .modifier(TopBarModifier())
        }

    }
}

struct InfoView_Previews: PreviewProvider {
    static var previews: some View {
        InfoView()
    }
}
