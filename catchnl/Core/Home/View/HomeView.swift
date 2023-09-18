//
//  LoginView.swift
//  catchnl
//
//  Created by Fatih Toker on 12.09.2023.
//

import SwiftUI

struct HomeView: View {
    @EnvironmentObject var contentViewModel: ContentViewModel
    @StateObject var viewModel = HomeViewModel()
    
    @State var isUploaded = false

    let incidents = [
                    "Wat is er gebeurd?",
                    "Hard rijden in een woonwijk",
                    "Geen richting aangeven bij het wisselen van rijbaan",
                    "Bumperkleven bij hoge snelheid",
                    "Agressief rijgedrag",
                    "Door rood licht rijden",
                    "Inhalen op een onveilige plek",
                    "Geen voorrang verlenen bij een gelijkwaardige kruising of zebrapad",
                    "Onveilig parkeren",
                    "Onnodig veel geluid maken, zoals hard gas geven of toeteren",
                    "anders"
                    ]

    
    var body: some View {
        NavigationView {
            VStack{
                switch viewModel.state {
                case .nothing:
                    ScrollView(.vertical){
                        Spacer()
                       
                        

                            
                        ZStack(alignment: .center){
                            Image("licencePlate")
                                .resizable()
                                .scaledToFit()
                            
                            TextField("", text: $viewModel.licencePlate)
                                .font(.custom(Font.Poppins_SemiBold, size: 30))
                                .keyboardType(.URL)
                                .textInputAutocapitalization(.characters)
                                .padding(.leading, 70)
                        }
                        
                        Text("Je kan deze maand nog  \(contentViewModel.currentUser.complaintPerMonthLeft ?? "0") auto's opgeven")
                            .font(.custom(Font.Poppins_Regular, size: 12))
                     
                        
                        VStack(alignment: .leading){
                            Text("Plaats")
                                .font(.custom(Font.Poppins_SemiBold, size: 20))
                                .padding(.leading)
                                .foregroundColor(Color("secondry-color"))
                                .padding(.bottom, -2)
                                
                            InputCellView(hint: "Plaats",systemImageName: "mappin" , text: $viewModel.place, state: .text)
                                .padding(.horizontal)
                            
                            Text("Datum (YYYY-MM-DD)")
                                .font(.custom(Font.Poppins_SemiBold, size: 20))
                                .padding(.leading)
                                .foregroundColor(Color("secondry-color"))
                                .padding(.top)
                                .padding(.bottom, -2)
                            InputCellView(hint: "YYYY-MM-DD",systemImageName: "calendar" , text: $viewModel.date, state: .text)
                                .padding(.horizontal)
                            
                            Text("Incident")
                                .font(.custom(Font.Poppins_SemiBold, size: 20))
                                .padding(.leading)
                                .foregroundColor(Color("secondry-color"))
                                .padding(.top, 10)
                                .padding(.bottom, -2)
                            
                            Picker("Wat is er gebeurd?", selection: $viewModel.incidentSelection){
                                ForEach(incidents, id: \.self) {
                                    Text($0)
                                        .frame(maxWidth: .infinity)
                                        .foregroundColor(Color("secondry-color"))
                                        .overlay {
                                            RoundedRectangle(cornerRadius: 10)
                                                .stroke(Color(.systemGray5), lineWidth: 2)
                                        }
                                }
                            }
                            .accentColor(Color("secondry-color"))
                            .frame(maxWidth: .infinity)
                            .foregroundColor(Color("secondry-color"))
                            .padding(5)
                            .overlay {
                                RoundedRectangle(cornerRadius: 10)
                                    .stroke(Color(.systemGray5), lineWidth: 2)
                            }
                            .padding(.horizontal)
                            .padding(.bottom)
                            
                            if viewModel.incidentSelection == "anders" {
                                InputCellView(hint: "Anders", systemImageName: "" , text: $viewModel.incident, state: .text)
                                    .padding(.horizontal)
                            }
                        }
                        .padding(.top)

                        Button {
                            viewModel.state = .loading
                            viewModel.uploadComplaint(userID: contentViewModel.currentUser.id!) { success in
                                if success {
                                    contentViewModel.updateProfile()
                                    isUploaded = true
                                    viewModel.licencePlate = ""
                                    viewModel.place = ""
                                    viewModel.date = ""
                                    viewModel.incident = ""
                                    viewModel.state = .nothing
                                } else {
                                    viewModel.state = .nothing
                                }
                            }
                        } label: {
                            Text("Verstuur")
                                .modifier(ButtonModifier())
                                .font(.custom(Font.Poppins_SemiBold, size: 18))
                        }
          
                        Spacer()
                    }
                    .padding(.horizontal, 5)
                    .alert(viewModel.errorMessage, isPresented: $viewModel.isError) {
                        Button("OK", role: .cancel) { }
                    }
                    .alert("Bedankt voor je melding", isPresented: $isUploaded){
                        Button("OK", role: .cancel) { }
                    }
                    
                case .loading:
                    ProgressView()
                    
                }
            }
            .alert(viewModel.errorMessage, isPresented: $viewModel.isError) {
                Button("OK", role: .cancel) { }
            }
            
            
            .modifier(TopBarModifier(isFromMain: true))
        }
    
    
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
